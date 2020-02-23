package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.vo.Data;

import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-23 16:30
 * @Description:
 */
public interface AdvertService {
    Page queryPage(Map paramMap);

    int insertAdvert(Advertisement advert);

    int deleteById(Integer id);

    int deleteBatchByVO(Data data);

    Advertisement getById(Integer id);

    int updateAdvert(Advertisement advert);
}
