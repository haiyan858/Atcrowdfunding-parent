package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-20 01:10
 * @Description: 访问权限拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    //@Autowired
    //private PermissionService permissionService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //获取系统所有许可权限菜单，必须包含请求路径url，否则拒绝访问
        // （后续可优化，每次拦截请求都要查询数据库，效率比较低）
        List<Permission> permissionList = permissionService.queryAllPermission();

        Set<String> allURIs = new HashSet<>();
        for (Permission permission: permissionList){
            if (StringUtil.isNotEmpty(permission.getUrl())){
                allURIs.add("/"+permission.getUrl());
            }
        }

        //改进效率：在服务器启动时加载所有数据，并存放到application域中 application.setAttribute()
       //f Set<String> allURIs = (Set<String>) request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URI);

        //1、首先判断请求的路径是不是已有的合法路径
        String requestURI = request.getRequestURI(); // /some/path.htm
        if (allURIs.contains(requestURI)){
            //2、再判断是否是分配的路径

            //获取当前用户的许可菜单，必须包含请求路径url，否则拒绝访问
            HttpSession session = request.getSession();
            //请求main的时候放进去的
            Set<String> myAuthURIs = (Set<String>) session.getAttribute(Const.MY_URIS);

            if (myAuthURIs.contains(requestURI)){
                return true;
            }else {
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }
        }else {
            return true; //不需要权限控制的(主页面等，任何人都可以访问的)，直接放行
        }

    }

}
