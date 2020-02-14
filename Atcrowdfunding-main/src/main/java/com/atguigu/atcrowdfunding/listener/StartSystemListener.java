package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-01-21 07:54
 * @Description:
 */
public class StartSystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("tomcat启动");

        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH",contextPath);

        System.out.println("1APP_PATH.......");
        System.out.println(contextPath);
        System.out.println("2APP_PATH.......");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("tomcat关闭");
    }
}
