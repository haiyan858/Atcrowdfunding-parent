package com.atguigu.atcrowdfunding.manager.dao;


import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-23 16:30
 * @Description:
 */
public interface AdvertMappper {
    List<Advertisement> queryList(Map paramMap);

    Integer queryCount(Map paramMap);

    int insert(Advertisement advert);

    int deleteByPrimaryKey(Integer id);

    //int deleteBatchByVO(@Param("advertList")List<User> datas);
    int deleteBatchByVO(Data datas);

    Advertisement selectByPrimaryKey(Integer id);

    int update(Advertisement advert);

}
