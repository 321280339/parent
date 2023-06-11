package com.atguigu.auth.service.impl;


import com.atguigu.auth.mapper.SysDeptMapper;
import com.atguigu.auth.service.SysDeptService;
import com.atguigu.auth.utils.DeptHelper;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.model.system.SysDept;
import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-05-23
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public List<SysDept> findDeptBydeptId(Long deptId) {
        //查询所有上级部门及下属部门
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getId,deptId);
        List<SysDept> allSysDeptList = baseMapper.selectList(wrapper);

        //根据上级部门id查询下降部门id
        LambdaQueryWrapper<SysDept> wrapperSysDept = new LambdaQueryWrapper<>();
        wrapperSysDept.eq(SysDept::getChildren,deptId);
        List<SysDept> sysDeptList = sysDeptService.list(wrapperSysDept);

        //根据上级部门id获取对应的下级部门
        List<List<SysDept>> collect = sysDeptList.stream().map(c -> c.getChildren()).collect(Collectors.toList());

        //拿着上级部门id和下级部门id进行比较相同封装
        allSysDeptList.stream().forEach(item -> {
            if(collect.contains(item.getId())){
                item.setSelect(true);
            }else {
                item.setSelect(false);
            }
        });

        List<SysDept> sysMenuList = DeptHelper.buildTree(allSysDeptList);

        return sysMenuList;
    }
}
