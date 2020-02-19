package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-18 16:58
 * @Description:
 */
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Permission> queryAllPermission();

    List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
