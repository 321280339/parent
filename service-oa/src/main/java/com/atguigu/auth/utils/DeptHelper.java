package com.atguigu.auth.utils;

import com.atguigu.model.system.SysDept;

import java.util.ArrayList;
import java.util.List;


public class DeptHelper {
    //使用递归方法建菜单
    public static List<SysDept> buildTree(List<SysDept> sysDeptList) {
        //创建list集合，用于最终数据
        List<SysDept> trees = new ArrayList<>();
        //把所有菜单数据进行遍历
        for(SysDept SysDept:sysDeptList) {
            //递归入口进入
            //parentId=0是入口
            if(SysDept.getParentId().longValue()==0) {
                trees.add(getChildrend(SysDept,sysDeptList));
            }
        }
        return trees;
    }

    private static SysDept  getChildrend(SysDept sysDept,
                                                  List<SysDept> sysDeptList) {
        sysDept.setChildren(new ArrayList<SysDept>());
        //遍历所有菜单数据，判断 id 和 parentId对应关系
        for(SysDept it: sysDeptList) {
            if(sysDept.getId().longValue() == it.getParentId().longValue()) {
                if (sysDept.getChildren() == null) {
                    sysDept.setChildren(new ArrayList<>());
                }
                sysDept.getChildren().add(getChildrend(it,sysDeptList));
            }
        }
        return sysDept;
    }

}
