package com.rabbiter.market.service.sale_management.sale_records;

import com.rabbiter.market.domain.sale_management.sale_records.SaleRecords;
import com.rabbiter.market.qo.sale_records.QuerySaleRecords;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ISaleRecordsService extends IService<SaleRecords> {
    List<Map<String, Object>> getOptionSaleRecordsGoods();

    SaleRecords saveSaleRecords(SaleRecords saleRecords, String token);

    Page<SaleRecords> queryPageByQoSaleRecords(QuerySaleRecords qo);

    void delSaleRecords(String cn);
}
