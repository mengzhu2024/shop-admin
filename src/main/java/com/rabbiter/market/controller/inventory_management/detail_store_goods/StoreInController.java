package com.rabbiter.market.controller.inventory_management.detail_store_goods;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoods;
import com.rabbiter.market.service.inventory_management.detail_store_goods.IDetailStoreGoodsService;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods_in")
public class StoreInController {
    @Autowired
    private IDetailStoreGoodsService detailStoreGoodsService;

    @HasPermisson("inventory_management:detail_store_goods_in:save")
    @PostMapping("/save")
    public JsonResult saveIn(DetailStoreGoods detailStoreGoods, HttpServletRequest request) {
        detailStoreGoodsService.saveIn(detailStoreGoods, (String) request.getHeader("token"));
        return JsonResult.success();
    }

    @HasPermisson("inventory_management:detail_store_goods_in:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryDetailStoreGoods qo) {
        Page<DetailStoreGoodsVo> page = detailStoreGoodsService.queryPageByQoIn(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_in:delIn")
    @PostMapping("/delIn")
    public JsonResult delIn(@NotEmpty(message = "系统编号不能为空") String cn) {
        detailStoreGoodsService.delIn(cn);
        return JsonResult.success();
    }
}
