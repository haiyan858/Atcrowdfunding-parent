package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/member")
    public String member(){
        //TODO 暂时这么写，后续可调整为会员页面
        return "main";
    }


    @RequestMapping("/main")
    public String main(HttpSession session){

        //加载当前登录用户所拥有的许可权限

        User user = (User) session.getAttribute(Const.LOGIN_USER);

        List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());

        Permission permissionRoot = null;

        Map<Integer,Permission> map = new HashMap<Integer, Permission>();
        for (Permission permission: myPermissions){
            map.put(permission.getId(),permission);
        }

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
        return "main";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session){

        //判断是否需要自动登录
        //如果之前登录过，cookie中存放了用户信息，需要获取cookie中的信息，再进行数据库的验证

        boolean needLogin = true;//是否需要登录，跳转到登录页面
        String logintype = "";

        Cookie[] cookies = request.getCookies(); //获取所有cookie
        if (cookies != null){
            for (Cookie cookie: cookies){
                if ("logincode".equals(cookie.getName())){

                    //"loginacct=superadmin&userpwd=202cb962ac59075b964b07152d234b70&logintype=user"
                    String logincode = cookie.getValue();
                    String[] split = logincode.split("&");
                    if (split.length == 3){
                        String loginacct = split[0].split("=")[1];
                        String userpwd = split[1].split("=")[1];
                        logintype = split[2].split("=")[1];
                        if ("user".equals(logintype)){



                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put("loginacct",loginacct);
                            //paramMap.put("userpswd",userpswd);
                            paramMap.put("userpswd", userpwd);
                            paramMap.put("type",logintype);

                            User dbLogin = userService.queryUserlogin(paramMap);

                            if (dbLogin != null){
                                session.setAttribute(Const.LOGIN_USER,dbLogin);
                                needLogin=false;
                            }

                            List<Permission> myPermissions = userService.queryPermissionByUserid(dbLogin.getId());

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

                            session.setAttribute(Const.MY_URIS,myAuthURIs);

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

                        }
                    }
                }
            }
        }

        if (needLogin){
            return "login";
        }else {
            if ("user".equals(logintype)){
                return "redirect:/main.htm";
            }else if ("member".equals(logintype)){
                return "redirect:/member.htm";
            }
        }

        return "login";
    }

    //注销登录
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        //session.removeAttribute(Const.LOGIN_USER);
        //return "redirect:/login.htm";
        return "redirect:/index.htm";
    }

    //注销登录
    @RequestMapping("/logoutUser")
    public String logoutUser(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies){
            if ("logincode".equals(cookie.getName())){
                System.out.println("123");
                Cookie delCookie = new Cookie("logincode",null);
                delCookie.setPath("/");//这一点很重要
                delCookie.setMaxAge(0);
                response.addCookie(delCookie);
            }
        }

        /*Cookie cookie = new Cookie("logincode", null);
        cookie.setPath("/");//这一点很重要
        cookie.setMaxAge(0);
        response.addCookie(cookie);*/

        return "redirect:/login.htm";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct,
                          String userpswd,
                          String type,
                          String rememberme,
                          HttpSession session, HttpServletResponse response){

        AjaxResult ajaxResult = new AjaxResult();

        try{
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct",loginacct);
            //paramMap.put("userpswd",userpswd);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type",type);

            User user = userService.queryUserlogin(paramMap);

            if ("1".equals(rememberme)){ //记住我2周
                String logincode = "\"loginacct="+user.getLoginacct()+"&userpwd="+user.getUserpswd()+"&logintype=user\"";
                System.out.println("cookie---->"+logincode);
                //cookie---->"loginacct=superadmin&userpwd=202cb962ac59075b964b07152d234b70&logintype=user"

                Cookie cookie = new Cookie("logincode", logincode);
                cookie.setMaxAge(60*60*24*14); //2周时间
                cookie.setPath("/"); //表示任何请求路径都可以访问Cookie

                response.addCookie(cookie);
            }



            session.setAttribute(Const.LOGIN_USER,user);

//        Map<Object, Object> map = new HashMap<>();
//        map.put("success",true);
//        map.put("message","登录成功");
////        return map; //ok

            //------------------------------------------------------------------------------------------------------------------------

            //加载当前登录用户所拥有的许可权限

            //User user = (User) session.getAttribute(Const.LOGIN_USER);

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

            session.setAttribute(Const.MY_URIS,myAuthURIs);

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
