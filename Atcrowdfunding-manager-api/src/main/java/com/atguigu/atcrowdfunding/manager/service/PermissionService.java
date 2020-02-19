package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-18 16:57
 * @Description:
 */
public interface PermissionService {
    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Permission> queryAllPermission();

    List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
