package com.rabbiter.market.service.inventory_management.detail_store_goods.impl;

import com.rabbiter.market.common.exception.BusinessException;
import com.rabbiter.market.common.cache.service.CacheService;
import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.domain.inventory_management.store.GoodsStore;
import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.mapper.inventory_management.detail_store_goods.DetailStoreGoodsMapper;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoods;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoodsOut;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.service.inventory_management.detail_store_goods.IDetailStoreGoodsService;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsOutVo;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsVo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class DetailStoreGoodsServiceImpl extends ServiceImpl<DetailStoreGoodsMapper, DetailStoreGoods> implements IDetailStoreGoodsService {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private IGoodsService goodsService;

    @Override
    public void saveIn(DetailStoreGoods detailStoreGoods, String token) {
        Goods goods = goodsService.getById(detailStoreGoods.getGoodsId());
        goods.setResidueNum(goods.getResidueNum() + detailStoreGoods.getGoodsNum());
        goodsService.updateById(goods);
        Employee employee = JSONObject.parseObject(cacheService.getCacheObject(token), Employee.class);
        detailStoreGoods.setType(DetailStoreGoods.TYPE_IN);
        detailStoreGoods.setCreateid(employee.getId());
        detailStoreGoods.setCreateby(employee.getNickName());
        detailStoreGoods.setCn(IdWorker.getIdStr());
        detailStoreGoods.setGoodsName(goods.getName());
        detailStoreGoods.setCreateTime(new Date());
        super.save(detailStoreGoods);
    }

    @Override
    public Page<DetailStoreGoodsVo> queryPageByQoIn(QueryDetailStoreGoods qo) {
        Page<DetailStoreGoods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        Page<DetailStoreGoodsVo> page1 = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<DetailStoreGoods> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        wrapper.like(StringUtils.hasText(qo.getGoodsName()), "goods_name", qo.getGoodsName());
        wrapper.ge(StringUtils.hasText(qo.getStartCreateTime()), "create_time", qo.getStartCreateTime());
        wrapper.le(StringUtils.hasText(qo.getEndCreateTime()), "create_time", qo.getEndCreateTime());
        wrapper.eq("type", DetailStoreGoods.TYPE_IN);
        super.page(page, wrapper);
        List<DetailStoreGoods> records = page.getRecords();
        if (records == null || records.size() <= 0) {
            page1.setTotal(0L);
        }
        List<DetailStoreGoodsVo> list = new ArrayList<>();
        for (DetailStoreGoods record : records) {
            DetailStoreGoodsVo vo = new DetailStoreGoodsVo();
            BeanUtils.copyProperties(record, vo);
            list.add(vo);
        }
        page1.setRecords(list);
        page1.setTotal(page.getTotal());
        return page1;
    }

    @Override
    public void delIn(String cn) {
        UpdateWrapper<DetailStoreGoods> wrapper = new UpdateWrapper<>();
        wrapper.eq("cn", cn);
        super.update(wrapper);
    }

    @Override
    public Page<DetailStoreGoodsOutVo> queryPageByQoOut(QueryDetailStoreGoodsOut qo) {
        Page<DetailStoreGoods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        Page<DetailStoreGoodsOutVo> page1 = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<DetailStoreGoods> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        wrapper.like(StringUtils.hasText(qo.getGoodsName()), "goods_name", qo.getGoodsName());
        wrapper.ge(StringUtils.hasText(qo.getStartCreateTime()), "create_time", qo.getStartCreateTime());
        wrapper.le(StringUtils.hasText(qo.getEndCreateTime()), "create_time", qo.getEndCreateTime());
        wrapper.eq("type", DetailStoreGoods.TYPE_OUT);
        super.page(page, wrapper);
        List<DetailStoreGoods> records = page.getRecords();
        if (records == null || records.size() <= 0) {
            page1.setTotal(0L);
        }
        List<DetailStoreGoodsOutVo> list = new ArrayList<>();
        for (DetailStoreGoods record : records) {
            DetailStoreGoodsOutVo vo = new DetailStoreGoodsOutVo();
            BeanUtils.copyProperties(record, vo);
            list.add(vo);
        }
        page1.setRecords(list);
        page1.setTotal(page.getTotal());
        return page1;
    }

    @Override
    public Map<String, Object> initOutOptions() {
        Set<Long> goodsIds = new HashSet<>();
        Set<Long> storeIds = new HashSet<>();
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<>();
        wrapper.gt("residue_num", 0L);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> goodsList = new ArrayList<>();
        List<Map<String, Object>> storeList = new ArrayList<>();
        List<Goods> goods = goodsService.listByIds(goodsIds);
        for (Goods good : goods) {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", good.getId());
            goodsMap.put("name", good.getName());
            goodsList.add(goodsMap);
        }
        map.put("goods", goodsList);
        map.put("stores", storeList);
        return map;
    }
}
