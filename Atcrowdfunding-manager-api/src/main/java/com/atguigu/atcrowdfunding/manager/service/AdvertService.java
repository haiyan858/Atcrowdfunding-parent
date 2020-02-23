package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.Page;

import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-23 16:30
 * @Description:
 */
public interface AdvertService {
    Page queryPage(Map paramMap);

    int insertAdvert(Advertisement advert);
}
