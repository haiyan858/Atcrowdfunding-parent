package com;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-08 22:52
 * @Description:
 */
public class Test4 {

//    @Test
//    public void test() {
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");
//        UserService userService = ioc.getBean(UserService.class);
//
//        for (int i = 100; i < 200; i++) {
//            User user = new User();
//            user.setLoginacct("test" + i);
//            user.setUserpswd(MD5Util.digest("123"));
//            user.setUsername("testName" + i);
//            user.setEmail("test" + i + "@atguigu.com");
//            user.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//
//            int save = userService.save(user);
//            System.out.println(save);
//        }
//    }

//        User user = new User();
//        user.setLoginacct("test111" );
//        user.setUserpswd(MD5Util.digest("123"));
//        user.setUsername("testName" );
//        user.setEmail("test@atguigu.com");
//        user.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
}
