package com.rabbiter.market.controller.system.menu;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.system.menu.Menu;
import com.rabbiter.market.qo.system.MenuQuery;
import com.rabbiter.market.service.system.menu.IMenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 条件分页查询菜单的信息
     * @param qo
     * @return
     */
    @HasPermisson("system:menu:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(MenuQuery qo) {
        Page<Menu> page = menuService.queryPageByQo(qo);
        return JsonResult.success(page);
    }
}
