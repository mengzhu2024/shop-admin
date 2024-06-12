package com.rabbiter.market.service.goods_management.goods.impl;

import com.rabbiter.market.common.exception.BusinessException;
import com.rabbiter.market.common.cache.constants.CacheKeys;
import com.rabbiter.market.common.cache.service.CacheService;
import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.goods_management.goods_category.GoodsCategory;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.domain.inventory_management.notice.NoticeIn;
import com.rabbiter.market.domain.inventory_management.notice.NoticeOut;
import com.rabbiter.market.domain.inventory_management.store.GoodsStore;
import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.mapper.goods_management.goods.GoodsMapper;
import com.rabbiter.market.qo.goods_management.goods.QueryGoods;
import com.rabbiter.market.qo.goods_management.goods_store.QueryGoodsStore;
import com.rabbiter.market.qo.goods_management.statistic_sale.QueryStatisticSale;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeIn;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeOut;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.service.goods_management.goods_category.IGoodsCategoryService;
import com.rabbiter.market.vo.goods.GoodsListVo;
import com.rabbiter.market.vo.goods_management.goods_store.GoodsStoreVo;
import com.rabbiter.market.vo.statistics.sale_management.SaleGoodsVo;
import com.rabbiter.market.vo.statistics.sale_management.SalesStatisticsVo;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private IGoodsCategoryService goodsCategoryService;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public Page<GoodsListVo> queryPageByQo(QueryGoods qo) {
        Page<GoodsListVo> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        ArrayList<GoodsListVo> volists = new ArrayList<>();
        Page<Goods> goodsPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>()
                .eq(qo.getId() != null, "id", qo.getId())
                .eq(qo.getSellPrice() != null, "sell_price", qo.getSellPrice())
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName())
                .eq(qo.getCategoryId() != null, "category_id", qo.getCategoryId())
                .eq(StringUtils.hasText(qo.getState()), "state", qo.getState())
                .ge(StringUtils.hasText(qo.getOperateStartTime()), "update_time", qo.getOperateStartTime())
                .le(StringUtils.hasText(qo.getOperateEndTime()), "update_time", qo.getOperateEndTime());
        super.page(goodsPage, wrapper);
        for (Goods record : goodsPage.getRecords()) {
            GoodsListVo vo = new GoodsListVo();
            BeanUtils.copyProperties(record, vo);
            volists.add(vo);
        }
        page.setRecords(volists);
        page.setTotal(goodsPage.getTotal());
        return page;
    }

    @Override
    public void saveGoods(Goods goods, String token) {
        Employee employee = JSONObject.parseObject(cacheService.getCacheObject(token), Employee.class);
        goods.setState(Goods.STATE_UP);
        goods.setCreateby(employee.getNickName());
        goods.setUpdateby(employee.getNickName());
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        if (goods.getCategoryId() != null) {
            GoodsCategory category = goodsCategoryService.getById(goods.getCategoryId());
            if (category != null) {
                goods.setCategoryName(category.getName());
            }
        }
        super.save(goods);
    }

    @Override
    public void updateGoods(Goods goods, String token) {
        Employee employee = JSONObject.parseObject(cacheService.getCacheObject(token), Employee.class);
        goods.setUpdateby(employee.getNickName());
        goods.setUpdateTime(new Date());
        if (goods.getCategoryId() != null) {
            GoodsCategory category = goodsCategoryService.getById(goods.getCategoryId());
            if (category != null) {
                goods.setCategoryName(category.getName());
            }
        }
        super.updateById(goods);
    }

    @Override
    public List<Map<String, Object>> selected_goodsAll() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP);
        List<Goods> list = super.list(wrapper);
        if (list==null&&list.size()==0){
            return null;
        }
        List<Map<String, Object>> listVo = new ArrayList<>();
        for (Goods goods : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",goods.getId());
            map.put("name",goods.getName());
            listVo.add(map);
        }

        return listVo;
    }

    @Override
    public Page<GoodsStoreVo> queryPageGoodsStore(QueryGoodsStore qo) {
        Page<GoodsStoreVo> page = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        Page<Goods> goodsPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP)
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        super.page(goodsPage,wrapper);
        if (goodsPage.getTotal()<=0) {
            page.setRecords(new ArrayList<>());
            page.setTotal(0);
            return page;
        }
        List<GoodsStoreVo> list = new ArrayList<>();
        for (Goods record : goodsPage.getRecords()) {
            GoodsStoreVo vo = new GoodsStoreVo();
            BeanUtils.copyProperties(record,vo);
            list.add(vo);
        }
        page.setTotal(goodsPage.getTotal());
        page.setRecords(list);
        return page;
    }

    @Override
    public GoodsStoreVo queryGoodsStoreById(Long id) {
        GoodsStoreVo vo = new GoodsStoreVo();
        Goods goods = super.getById(id);
        BeanUtils.copyProperties(goods,vo);
        return vo;
    }

    @Override
    public Page<NoticeIn> queryPageNoticeIn(QueryNoticeIn qo) {
        Page<NoticeIn> noticeInPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<NoticeIn> list = new ArrayList<>();
        int start=(qo.getCurrentPage()-1)*qo.getPageSize();
        Map<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("size",qo.getPageSize());
        if (StringUtils.hasLength(qo.getName())){
            map.put("name",qo.getName());
        }
        int totalCount=goodsMapper.getNoticeInTotalCount(map);
        list=goodsMapper.getNoticePageList(map);
        noticeInPage.setTotal(totalCount);
        noticeInPage.setRecords(list);
        return noticeInPage;
    }

    @Override
    public Page<NoticeOut> queryPageNoticeOut_shelves(QueryNoticeOut qo) {
        Page<NoticeOut> noticeOutPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<NoticeOut> list = new ArrayList<>();
        int start=(qo.getCurrentPage()-1)*qo.getPageSize();
        Map<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("size",qo.getPageSize());
        if (StringUtils.hasLength(qo.getName())){
            map.put("name",qo.getName());
        }
        int totalCount=goodsMapper.getNoticeOutShelvesTotalCount(map);
        list=goodsMapper.getNoticeShelvesPageList(map);
        noticeOutPage.setTotal(totalCount);
        noticeOutPage.setRecords(list);
        return noticeOutPage;
    }

    @Override
    public SalesStatisticsVo queryPageStatisticSaleByQo(QueryStatisticSale qo) {
       Long total=goodsMapper.queryPageStatisticSaleByQo(qo.getName());
        SalesStatisticsVo vo = new SalesStatisticsVo();
        vo.setTotal(total);
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP)
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        Page<Goods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        super.page(page,wrapper);
        Page<SaleGoodsVo> saleGoodsVoPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        saleGoodsVoPage.setTotal(page.getTotal());
        List<SaleGoodsVo> saleGoodsVos = new ArrayList<>();
        for (Goods record : page.getRecords()) {
            SaleGoodsVo goodsVo = new SaleGoodsVo();
            goodsVo.setGoodsId(record.getId());
            goodsVo.setGoodsName(record.getName());
            goodsVo.setSalesVolume(record.getSalesVolume());
            goodsVo.setPercentage(total);
            goodsVo.setCoverUrl(record.getCoverUrl());
            saleGoodsVos.add(goodsVo);
        }
        saleGoodsVoPage.setRecords(saleGoodsVos);
        vo.setVos(saleGoodsVoPage);
        return vo;
    }

}
