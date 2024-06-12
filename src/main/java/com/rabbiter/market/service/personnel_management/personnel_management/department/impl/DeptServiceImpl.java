package com.rabbiter.market.service.personnel_management.personnel_management.department.impl;

import com.rabbiter.market.common.exception.BusinessException;
import com.rabbiter.market.domain.personnel_management.department.Dept;
import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.rabbiter.market.mapper.personnel_management.department.DeptMapper;
import com.rabbiter.market.qo.personnel_management.department.QueryDept;
import com.rabbiter.market.service.personnel_management.employee.IEmployeeService;
import com.rabbiter.market.service.personnel_management.personnel_management.department.IDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    @Autowired
    private IEmployeeService employeeService;
    @Override
    public List<Dept> listByQo(QueryDept qo) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<Dept>()
                .like(StringUtils.hasText(qo.getName()),Dept::getName, qo.getName())
                .eq(StringUtils.hasText(qo.getState()),Dept::getState, qo.getState());
        return super.list(wrapper);
    }

    @Transactional
    @Override
    public void forbiddenRole(Long id) {
        QueryWrapper<Employee> empWrapper = new QueryWrapper<Employee>().eq(id != null,"deptId", id);
        List<Employee> list = employeeService.list(empWrapper);
        if (list!=null &&list.size()>0){
            throw new BusinessException("操作失败，该部门正在使用");
        }
        UpdateWrapper<Dept> wrapper = new UpdateWrapper<Dept>().set("state", Dept.STATE_BAN).eq("id", id);
        super.update(wrapper);
    }

    @Transactional
    @Override
    public void saveDept(Dept dept) {
        //判断是否有被创建
        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>().eq(StringUtils.hasText(dept.getName()), "name", dept.getName());
        if (super.getOne(wrapper)!=null){
            throw new BusinessException("操作失败，该部门已存在");
        }
        dept.setState(Dept.STATE_NORMAL);
        super.save(dept);

    }

    @Transactional
    @Override
    public void updateDept(Dept dept) {
        if (Dept.STATE_BAN.equals(dept.getState())){
            QueryWrapper<Employee> empWrapper = new QueryWrapper<Employee>().eq(dept.getId() != null,"deptId", dept.getId());
            List<Employee> list = employeeService.list(empWrapper);
            if (list!=null &&list.size()>0){
                throw new BusinessException("操作失败，该部门正在使用");
            }
        }
        super.updateById(dept);
    }
}
