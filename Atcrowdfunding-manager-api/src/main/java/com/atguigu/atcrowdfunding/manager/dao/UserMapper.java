package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-04 15:31
 * @Description:
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User queryUserlogin(Map<String,Object> paramMap);

    List<User> queryUserPage(Integer pageno, Integer pagesize);

    //List<User> queryList(@Param("startIndex") Integer startIndex, @Param("pagesize") Integer pagesize);
    List<User> queryList(Map paramMap);

    //Integer queryCount();
    Integer queryCount(Map paramMap);

    //int deleteBatchUserByVO(Data data);
    //int deleteBatchUserByVO(List<User> userList);
    int deleteBatchUserByVO(@Param("userList") List<User> userList);

}
