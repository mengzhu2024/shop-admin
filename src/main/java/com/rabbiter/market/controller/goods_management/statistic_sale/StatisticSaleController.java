package com.rabbiter.market.controller.goods_management.statistic_sale;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.qo.goods_management.statistic_sale.QueryStatisticSale;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.vo.statistics.sale_management.SalesStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/goods_management/statistic_sale")
public class StatisticSaleController {
    @Autowired
    private IGoodsService goodsService;

    @HasPermisson("goods_management:statistic_sale:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryStatisticSale qo) {
        SalesStatisticsVo vo = goodsService.queryPageStatisticSaleByQo(qo);
        return JsonResult.success(vo);
    }

}
