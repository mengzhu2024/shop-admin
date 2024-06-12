package com.rabbiter.market.service.system.menu;

import com.rabbiter.market.domain.system.menu.Menu;
import com.rabbiter.market.qo.system.MenuQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface IMenuService extends IService<Menu> {
    /**
     * 查询所有的菜单
     * @return
     */
    List<Menu> findAll();

    /**
     * 根据角色id集合查询对应的菜单
     * @param rids
     * @return
     */
    List<Menu> queryByRids(Set<Long> rids);

    /**
     * 分页查询菜单的信息
     * 以目录进行分页
     * @param qo
     * @return
     */
    Page<Menu> queryPageByQo(MenuQuery qo);


    /**
     * 查询父菜单id
     * @param ids
     * @return
     */
    List<Long> listParentByIds(List<Long> ids);
}
