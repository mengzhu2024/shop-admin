package com.rabbiter.market.controller.inventory_management.notice;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.domain.inventory_management.notice.NoticeIn;
import com.rabbiter.market.domain.inventory_management.notice.NoticeOut;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeIn;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeOut;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.vo.detail_store_goods.notice.NoticeInNotNormalVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods/notice")
public class NoticeController {
    @Autowired
    private IGoodsService goodsService;

    @HasPermisson("inventory_management:detail_store_goods_in:notice:list")
    @PostMapping("/queryPageNoticeIn")
    public JsonResult queryPageNoticeIn(QueryNoticeIn qo) {
        Page<NoticeIn> page = goodsService.queryPageNoticeIn(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:notice:list")
    @PostMapping("/queryPageNoticeOut_shelves")
    public JsonResult queryPageNoticeOut_shelves(QueryNoticeOut qo) {
        Page<NoticeOut> page = goodsService.queryPageNoticeOut_shelves(qo);
        return JsonResult.success(page);
    }

}
