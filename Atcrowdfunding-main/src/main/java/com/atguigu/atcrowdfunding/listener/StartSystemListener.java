package com.atguigu.atcrowdfunding.listener;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-01-21 07:54
 * @Description:
 */
public class StartSystemListener implements ServletContextListener {

    //在服务器启动时,创建application对象时需要执行的方法.
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextPath = servletContext.getContextPath();

        //1.将项目上下文路径(request.getContextPath())放置到application域中.
        servletContext.setAttribute("APP_PATH", contextPath);
        System.out.println("APP_PATH...");

       /*
       //2.加载所有许可路径
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        PermissionService permissionService = ioc.getBean(PermissionService.class);

        List<Permission> queryAllPermission = permissionService.queryAllPermission();

        Set<String> allURIs = new HashSet<String>();

        for (Permission permission : queryAllPermission) {
            allURIs.add("/"+permission.getUrl());
        }

        servletContext.setAttribute(Const.ALL_PERMISSION_URI, allURIs);*/
    }

    /*@Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("tomcat启动");

        //1 将项目上下文路径(request.getContextPath()) 放置到application域中
        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH",contextPath);

        System.out.println("1APP_PATH.......");
        System.out.println(contextPath);
        System.out.println("2APP_PATH.......");

        //2 加载所有权限许可数据
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
//        ApplicationContext ioc = getWebApplicationContext(application);
        PermissionService permissionService = ioc.getBean(PermissionService.class); //获取bean

        List<Permission> permissionList = permissionService.queryAllPermission();
        Set<String> allURIs = new HashSet<>();
        for (Permission permission: permissionList){
            if (StringUtil.isNotEmpty(permission.getUrl())){
                allURIs.add("/"+permission.getUrl());
            }
        }

        application.setAttribute(Const.ALL_PERMISSION_URI,allURIs);
        System.out.println("加载所有权限数据:"+allURIs.size());
    }*/

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("tomcat关闭");
    }
}
