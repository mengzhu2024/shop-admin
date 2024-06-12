package com.rabbiter.market.mapper.system.menu;

import com.rabbiter.market.domain.system.menu.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 根据角色id集合查询权限信息
     * @param rids
     * @return
     */
    List<Menu> queryByRids(Set<Long> rids);


}
