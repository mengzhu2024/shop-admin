package com.rabbiter.market.controller.login;

import com.rabbiter.market.common.sercurity.annotation.NoRequireLogin;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.system.menu.Menu;
import com.rabbiter.market.service.login.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
@RestController
@Validated
public class LoginEmpController {
    @Autowired
    private ILoginService loginService;

    /**
     * 登入功能
     *
     * @param username
     * @param password
     * @return
     */
    @NoRequireLogin
    @PostMapping("/login")
    public JsonResult login(@NotEmpty(message = "账号不能为空") String username, @NotEmpty(message = "密码不能为空") String password) {
        Map<String, Object> map = loginService.login(username, password);
        return JsonResult.success(map);

    }


    /**
     * 退出功能
     * @return
     */
    @NoRequireLogin
    @GetMapping("/exit")
    public JsonResult exit(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.hasLength(token)){
            loginService.exit(token);
        }
        return JsonResult.success();
    }
    @PostMapping("/logout")
    public JsonResult logout(@NotEmpty(message = "内容不能为空") String content,HttpServletRequest request) {

        loginService.logout(request.getHeader("token"), content);

        return JsonResult.success();
    }

    /**
     * 查询登录者的拥有的菜单
     * @param request
     * @return
     */
    @GetMapping("/empMenu")
    public JsonResult empMenu(HttpServletRequest request){
        List<Menu> menus=loginService.empMenu(request.getHeader("token"));
        return JsonResult.success(menus);
    }
    @NoRequireLogin
    @GetMapping("/checkedToken")
    public JsonResult checkedToken(String token){
        Map<String,Object> map=loginService.checkedToken(token);
        return JsonResult.success(map);
    }
}
