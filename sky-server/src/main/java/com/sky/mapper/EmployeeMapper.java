package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工数据访问对象（DAO）接口
 * 提供对员工数据的访问方法
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}