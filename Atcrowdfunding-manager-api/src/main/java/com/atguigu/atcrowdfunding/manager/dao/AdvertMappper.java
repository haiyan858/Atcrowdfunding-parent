package com.atguigu.atcrowdfunding.manager.dao;


import com.atguigu.atcrowdfunding.bean.Advertisement;

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
}
