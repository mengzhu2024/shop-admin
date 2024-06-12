package com.rabbiter.market.controller.inventory_management.detail_store_goods;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoodsOut;
import com.rabbiter.market.service.inventory_management.detail_store_goods.IDetailStoreGoodsService;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsOutVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods_out")
public class StoreOutController {
    @Autowired
    private IDetailStoreGoodsService detailStoreGoodsService;
    @HasPermisson("inventory_management:detail_store_goods_out:list")
    @PostMapping("/queryPageByQoOut")
    public JsonResult queryPageByQoOut(QueryDetailStoreGoodsOut qo){
        Page<DetailStoreGoodsOutVo> page=detailStoreGoodsService.queryPageByQoOut(qo);
        return JsonResult.success(page);
    }
    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @GetMapping("/initOutOptions")
    public JsonResult<Map<String,Object>> initOutOptions(){
        return JsonResult.success(detailStoreGoodsService.initOutOptions());
    }

    @HasPermisson("inventory_management:detail_store_goods_out:delOut")
    @PostMapping("/delOut")
    public JsonResult delOut(@NotEmpty(message = "系统编号不能为空") String cn) {
        detailStoreGoodsService.delIn(cn);
        return JsonResult.success();
    }
}
