package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-18 16:58
 * @Description:
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    @Override
    public List<Permission> getChildrenPermissionByPid(Integer id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }

    @Override
    public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
        return permissionMapper.queryPermissionidsByRoleid(roleid);
    }

    @Override
    public int savePermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public Permission queryPermissionByPrimaryKeyId(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

}
