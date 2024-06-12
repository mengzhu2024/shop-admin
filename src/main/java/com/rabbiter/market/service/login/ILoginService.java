package com.rabbiter.market.service.login;

import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.domain.system.menu.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 员工登录业务接口
 */
public interface ILoginService extends IService<Employee> {
    /**
     * 处理员工登录业务
     * @param username
     * @param password
     * @return
     */
    Map<String,Object> login(String username, String password);

    /**
     * 退出功能
     * @param token
     */
    void exit(String token);

    /**
     * 注销账户功能
     * @param token
     * @param content
     */
    void logout(String token, String content);

    /**
     * 登录者拥有的菜单信息
     * @param token
     * @return
     */
    List<Menu> empMenu(String token);

    Map<String, Object> checkedToken(String token);
}
