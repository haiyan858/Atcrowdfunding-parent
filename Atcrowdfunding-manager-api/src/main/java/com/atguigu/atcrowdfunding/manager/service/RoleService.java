package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-16 13:26
 * @Description:
 */
public interface RoleService {
    Page queryPage(Map paramMap);

    int save(Role role);

    int deleteByPrimaryKey(Integer uid);

    Role selectByPrimaryKey(Integer id);

    int updateRole(Role role);

    int batchDelete(Data data);
}
