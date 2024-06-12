package com.rabbiter.market.controller.personal;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.qo.personal.QueryEditPwd;
import com.rabbiter.market.service.personnel_management.employee.IEmployeeService;
import com.rabbiter.market.vo.employee.InformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 修改个人的密码
     * @param request
     * @param editPwd
     * @return
     */
    @HasPermisson("personal:edit_pwd")
    @PostMapping("/edit_pwd")
    public JsonResult edit_pwd(HttpServletRequest request, QueryEditPwd editPwd){
        String token = request.getHeader("token");
        employeeService.edit_pwd(editPwd,token);
        return JsonResult.success();
    }
    @HasPermisson("personnel_management:employee:update")
    @GetMapping("/information")
    public JsonResult information(HttpServletRequest request){
        String token = request.getHeader("token");
        InformationVo vo=employeeService.information(token);
        return JsonResult.success(vo);
    }
}
