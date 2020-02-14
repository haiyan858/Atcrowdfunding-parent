package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.manager.dao.TestDao;
import com.atguigu.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-01-19 23:30
 * @Description:
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public void insert() {
        Map map = new HashMap<>();
        map.put("name","zhang3");

        testDao.insert(map);
        System.out.println("insert....");
    }
}
