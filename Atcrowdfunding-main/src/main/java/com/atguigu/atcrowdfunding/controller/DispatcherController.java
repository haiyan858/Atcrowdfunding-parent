package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Cont;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-01-21 08:15
 * @Description:
 */

@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;


    @RequestMapping("/main")
    public String main(HttpSession session){

        /*//加载当前登录用户所拥有的许可权限

        User user = (User) session.getAttribute(Cont.LOGIN_USER);

        List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());

        Permission permissionRoot = null;

        //存放用户已分配的uri
        Set<String> myAuthURIs = new HashSet<>(); //用于拦截器拦截许可权限

        Map<Integer,Permission> map = new HashMap<Integer, Permission>();
        for (Permission permission: myPermissions){
            map.put(permission.getId(),permission);
            if (StringUtil.isNotEmpty(permission.getUrl())){
                myAuthURIs.add("/"+permission.getUrl());
            }
        }

        session.setAttribute(Cont.MY_URIS,myAuthURIs);

        for (Permission permission: myPermissions){
            //Permission child = permission; //假设为子菜单
            permission.setOpen(true);
            if (permission.getPid() == null){
                permissionRoot = permission;
            }else {
                //父节点
                Permission parent = map.get(permission.getPid());
                parent.getChildren().add(permission);
            }
        }

        //sessionScope.permissionRoot.children
        session.setAttribute("permissionRoot",permissionRoot);*/
        return "main";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //注销登录
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        //session.removeAttribute(Cont.LOGIN_USER);
        //return "redirect:/login.htm";
        return "redirect:/index.htm";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, HttpSession session){

        AjaxResult ajaxResult = new AjaxResult();

        try{
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct",loginacct);
            //paramMap.put("userpswd",userpswd);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type",type);

            //<tx:method name="query*" read-only="true"/>
            User user = userService.queryUserlogin(paramMap);

            session.setAttribute(Cont.LOGIN_USER,user);

//        Map<Object, Object> map = new HashMap<>();
//        map.put("success",true);
//        map.put("message","登录成功");
////        return map; //ok

            //------------------------------------------------------------------------------------------------------------------------

            //加载当前登录用户所拥有的许可权限

            //User user = (User) session.getAttribute(Cont.LOGIN_USER);

            List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());

            Permission permissionRoot = null;

            //存放用户已分配的uri
            Set<String> myAuthURIs = new HashSet<>(); //用于拦截器拦截许可权限

            Map<Integer,Permission> map = new HashMap<Integer, Permission>();
            for (Permission permission: myPermissions){
                map.put(permission.getId(),permission);
                if (StringUtil.isNotEmpty(permission.getUrl())){
                    myAuthURIs.add("/"+permission.getUrl());
                }
            }

            session.setAttribute(Cont.MY_URIS,myAuthURIs);

            for (Permission permission: myPermissions){
                //Permission child = permission; //假设为子菜单
                permission.setOpen(true);
                if (permission.getPid() == null){
                    permissionRoot = permission;
                }else {
                    //父节点
                    Permission parent = map.get(permission.getPid());
                    parent.getChildren().add(permission);
                }
            }

            //sessionScope.permissionRoot.children
            session.setAttribute("permissionRoot",permissionRoot);


            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("登录成功");
        }catch (Exception e){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("登录失败");
            e.printStackTrace();
        }

        return ajaxResult;

//        return "{\"success\":true,\"message\":\"登录成功!\"}";
//        return "redirect:/main.htm"; //重定向， 即使重新刷新页面也是刷的main页面
//        return "main"; //doLogin 登录动作和这个是一个转发的关系，刷新页面会重复提交表单
    }


    //同步请求:
	/*@RequestMapping("/doLogin")
	public String doLogin(String loginacct,String userpswd,String type,HttpSession session){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		paramMap.put("type", type);

		User user = userService.queryUserlogin(paramMap);

		session.setAttribute(Const.LOGIN_USER, user);

		return "redirect:/main.htm";
	}*/


}
