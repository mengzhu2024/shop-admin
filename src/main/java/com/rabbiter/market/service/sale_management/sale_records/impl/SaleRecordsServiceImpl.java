package com.rabbiter.market.service.sale_management.sale_records.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.rabbiter.market.common.cache.service.CacheService;
import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.domain.member_management.member.Member;
import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.domain.sale_management.detail_sale_records.DetailSaleRecords;
import com.rabbiter.market.domain.sale_management.sale_records.SaleRecords;
import com.rabbiter.market.mapper.sale_management.sale_records.SaleRecordsMapper;
import com.rabbiter.market.qo.sale_records.QuerySaleRecords;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.service.inventory_management.detail_store_goods.IDetailStoreGoodsService;
import com.rabbiter.market.service.member_management.member.IMemberService;
import com.rabbiter.market.service.sale_management.detail_sale_records.IDetailSaleRecordsService;
import com.rabbiter.market.service.sale_management.sale_records.ISaleRecordsService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SaleRecordsServiceImpl extends ServiceImpl<SaleRecordsMapper, SaleRecords> implements ISaleRecordsService {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private IDetailSaleRecordsService detailSaleRecordsService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IDetailStoreGoodsService detailStoreGoodsService;

    @Override
    public List<Map<String, Object>> getOptionSaleRecordsGoods() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().gt("residue_num", 0L).eq("state", "0");
        List<Goods> list = goodsService.list(wrapper);
        List<Map<String, Object>> goodsList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Goods goods : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", goods.getId());
                map.put("name", goods.getName());
                map.put("residueNum", goods.getResidueNum());
                goodsList.add(map);
            }
        }
        return goodsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaleRecords saveSaleRecords(SaleRecords saleRecords, String token) {
        Employee employee = JSON.parseObject(cacheService.getCacheObject(token), Employee.class);
        saleRecords.setEid(employee.getId());
        saleRecords.setSellTime(new Date());
        saleRecords.setSellby(employee.getNickName());
        saleRecords.setState(SaleRecords.STATE_NORMAL);
        List<DetailStoreGoods> storeGoodsList = new ArrayList<>();
        for (DetailSaleRecords detailSaleRecord : saleRecords.getDetailSaleRecords()) {
            detailSaleRecord.setSellCn(saleRecords.getCn());
            Goods goods = goodsService.getById(detailSaleRecord.getGoodsId());
            DetailStoreGoods storeGoods = new DetailStoreGoods();
            storeGoods.setCn(IdWorker.getIdStr());
            storeGoods.setGoodsName(goods.getName());
            storeGoods.setGoodsId(goods.getId());
            storeGoods.setGoodsNum(detailSaleRecord.getGoodsNum());
            storeGoods.setType(DetailStoreGoods.TYPE_OUT);
            storeGoods.setCreateid(employee.getId());
            storeGoods.setCreateTime(new Date());
            storeGoods.setCreateby(employee.getNickName());
            storeGoods.setInfo("销售出库");
            storeGoodsList.add(storeGoods);

            UpdateWrapper<Goods> wrapper = new UpdateWrapper<Goods>()
                    .set("sales_volume", goods.getSalesVolume() != null ? goods.getSalesVolume() + detailSaleRecord.getGoodsNum() : detailSaleRecord.getGoodsNum())
                    .set("residue_num", goods.getResidueNum() - detailSaleRecord.getGoodsNum())
                    .eq("id", goods.getId());
            goodsService.update(wrapper);
        }
        detailSaleRecordsService.saveBatch(saleRecords.getDetailSaleRecords());
        detailStoreGoodsService.saveBatch(storeGoodsList);
        super.save(saleRecords);
        if ("1".equals(saleRecords.getType())) {
            //为会员增加积分 积分规则：积分=总金额*5%取整
            String s = saleRecords.getSellTotalmoney() * 0.05 + "";
            long integral = Long.parseLong(s.split("\\.")[0]);
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<Member>().eq("phone", saleRecords.getMemberPhone());
            Member member = memberService.getOne(memberQueryWrapper);
            UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<Member>()
                    .set("integral", member.getIntegral() != null ? member.getIntegral() +
                            integral : integral)
                    .eq("phone", saleRecords.getMemberPhone());
            memberService.update(memberUpdateWrapper);
        }
        return saleRecords;
    }

    @Override
    public Page<SaleRecords> queryPageByQoSaleRecords(QuerySaleRecords qo) {
        Page<SaleRecords> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<SaleRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", SaleRecords.STATE_NORMAL);
        queryWrapper.eq(StringUtils.hasText(qo.getType()), "type", qo.getType());
        queryWrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        queryWrapper.ge(StringUtils.hasText(qo.getStartSellTime()), "sell_time", qo.getStartSellTime());
        queryWrapper.le(StringUtils.hasText(qo.getEndSellTime()), "sell_time", qo.getEndSellTime());
        queryWrapper.eq(StringUtils.hasText(qo.getSellway()), "sellway", qo.getSellway());
        super.page(page, queryWrapper);
        List<SaleRecords> records = page.getRecords();
        if (records != null && records.size() > 0) {
            for (SaleRecords record : records) {
                QueryWrapper<DetailSaleRecords> sellCnWrapper = new QueryWrapper<DetailSaleRecords>().eq("sell_cn", record.getCn());
                List<DetailSaleRecords> list = detailSaleRecordsService.list(sellCnWrapper);
                record.setDetailSaleRecords(list);
            }
        }
        return page;
    }

    @Override
    public void delSaleRecords(String cn) {
        UpdateWrapper<SaleRecords> wrapper = new UpdateWrapper<>();
        wrapper.eq("cn", cn);
        wrapper.set("state", SaleRecords.STATE_DEL);
        super.update(wrapper);
    }

}
