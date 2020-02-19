package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-04 15:30
 * @Description:
 */
public interface UserService {
    int save(User user);
    User queryUserlogin(Map<String, Object> paramMap);
    List<User> queryUserPage(Integer pageno, Integer pagesize);
    Page queryPage(Map paramMap);
    User getUserById(Integer id);

    int updateUser(User user);

    int deleteUserById(Integer id);

    int deleteBatchUserById(Integer[] ids);

    int deleteBatchUserByVO(Data data);

    List<Integer> queryRoleByUserId(Integer id);

    List<Role> queryAllRole();

    int saveUserRoleRelationship(Integer userid, Data data);

    int deleteUserRoleRelationship(Integer userid, Data data);

    List<Permission> queryPermissionByUserid(Integer id);
}
