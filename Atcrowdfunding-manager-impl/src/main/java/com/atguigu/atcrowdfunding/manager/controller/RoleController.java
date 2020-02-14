package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-07 13:40
 * @Description:
 */
@Controller
public class RoleController {
    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(){
        return "assignPermission";
    }
}
