package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-16 13:26
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page queryPage(Map paramMap) {
        Page page = new Page((Integer)paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));

        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);

        List<Role> roleList = roleMapper.queryList(paramMap);
        page.setDatas(roleList);

        Integer totalsize  = roleMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);

        return page;
    }

    @Override
    public int save(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int deleteByPrimaryKey(Integer uid) {
        return roleMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int batchDelete(Data data) {
        return roleMapper.batchDelete(data);
    }

    //先删除原先的所有权限，再分配新的权限
    @Override
    public int saveRolePermissionRelationship(Integer roleid, Data datas) {
        roleMapper.deleteRolePermissionRelationship(roleid);//删除原先的所有权限

        int totalcount = 0;
        List<Integer> ids = datas.getIds();
        for (Integer permissionid : ids){
            RolePermission rp = new RolePermission();
            rp.setRoleid(roleid);
            rp.setPermissionid(permissionid);
            int count = roleMapper.insertRolePermission(rp); //分配新的权限
            totalcount += count;
        }

        return totalcount;
    }
}
