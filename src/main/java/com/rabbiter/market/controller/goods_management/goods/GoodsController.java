package com.rabbiter.market.controller.goods_management.goods;

import com.rabbiter.market.common.sercurity.annotation.NoRequireLogin;
import com.rabbiter.market.common.util.PathUtils;
import com.rabbiter.market.common.util.UploadUtil;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.qo.goods_management.goods.QueryGoods;
import com.rabbiter.market.service.goods_management.goods.IGoodsService;
import com.rabbiter.market.vo.goods.GoodsListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/goods_management/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    /*查询信息*/
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryGoods qo) {
        Page<GoodsListVo> page = goodsService.queryPageByQo(qo);
        return JsonResult.success(page);
    }
    /**
     * 上传图片到阿里云oss
     * 返回网络图片地址,uploaded:1:成功 0:失败
     *
     * @param upload
     * @return
     */
    @NoRequireLogin
    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile upload) {
        Map<String, Object> map = new HashMap<>();
        if (upload != null && upload.getSize() > 0) {
            String path = "";
            try {
                path = PathUtils.upload(upload);
                map.put("uploaded", 1);  //成功
                map.put("url", path);  //成功
            } catch (Exception e) {
                e.printStackTrace();
                map.put("uploaded", 0);  //失败
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", e.getMessage());
                map.put("error", errorMap);
            }
        } else {
            map.put("uploaded", 0);  //失败
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "上传失败，图片文件异常");
            map.put("error", errorMap);
        }
        return map;
    }
    /*保存*/
    @PostMapping("/save")
    public JsonResult saveGoods(Goods goods, HttpServletRequest request){
        goodsService.saveGoods(goods,(String) request.getHeader("token"));
        return JsonResult.success();
    }
    /*上/下架*/

    @PostMapping("/upOrdown")
    public JsonResult upOrdown(@NotNull(message = "商品编号不能为空") Long gid, String state,HttpServletRequest request){
        Goods goods = new Goods();
        goods.setId(gid);
        goods.setState(state);
        goodsService.updateById(goods);
        return JsonResult.success();
    }
    @GetMapping("/queryGoodsById")
    public JsonResult queryGoodsById(@NotNull(message = "商品编号不能为空") Long id){
        return JsonResult.success(goodsService.getById(id));
    }

    @PostMapping("/update")
    public JsonResult update(Goods goods, HttpServletRequest request){
        goodsService.updateGoods(goods, request.getHeader("token"));
        return JsonResult.success();
    }

    @GetMapping("/selected_goodsAll")
    public JsonResult selected_goodsAll(){
        List<Map<String,Object>> list=goodsService.selected_goodsAll();
        return JsonResult.success(list);
    }

}
