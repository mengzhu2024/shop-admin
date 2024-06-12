package com.rabbiter.market.service.personnel_management.employee;

import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.qo.personal.QueryEditPwd;
import com.rabbiter.market.qo.personnel_management.employee.QueryEmp;
import com.rabbiter.market.vo.employee.DetailEmpVo;
import com.rabbiter.market.vo.employee.EditEmpVo;
import com.rabbiter.market.vo.employee.InformationVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IEmployeeService  extends IService<Employee> {


    /**
     * 修改个人密码业务
     * @param editPwd
     * @param token
     */
    void edit_pwd(QueryEditPwd editPwd, String token);

    /**
     * 分页显示数据
     * @param qo
     * @return
     */
    Page<Employee> pageByQo(QueryEmp qo);

    DetailEmpVo detail(Long uid);

    void saveEmp(Employee employee,String token);

    EditEmpVo editbtn(Long uid);

    void updateEmp(Employee employee,String token);

    void deactivate(Long id);

    /**
     * 重置密码
     * @param eid
     */
    void resetPwd(Long eid);

    InformationVo information(String token);
}
