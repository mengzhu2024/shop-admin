package com.rabbiter.market.mapper.personnel_management.employee;

import com.rabbiter.market.domain.personnel_management.employee.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
