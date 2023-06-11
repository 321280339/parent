package com.atguigu.auth.service;


import com.atguigu.model.system.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-05-23
 */
public interface SysDeptService extends IService<SysDept> {

    //查询所有部门及岗位
    List<SysDept> findDeptBydeptId(Long deptId);

}
