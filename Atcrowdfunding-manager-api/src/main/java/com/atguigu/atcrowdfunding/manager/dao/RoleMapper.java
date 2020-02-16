package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Role;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-16 13:26
 * @Description:
 */
public interface RoleMapper {
    List<Role> queryList(Map paramMap);

    Integer queryCount(Map paramMap);
}
