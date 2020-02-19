package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Cont;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-20 00:40
 * @Description: 登录权限拦截器
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //1 定义哪些路径不需要拦截（白名单）
        Set<String> url = new HashSet<>();
        url.add("/user/reg.do");
        url.add("/user/reg.htm");
        url.add("/login.do");
        url.add("/login.htm");
        url.add("/doLogin.do");
        url.add("/logout.htm");

        String servletPath = request.getServletPath(); //请求路径
        if (url.contains(servletPath)){
            return true; //放行， 之后的代码不再执行， 但是后面的拦截器链还会继续执行
        }

        //2 判断用户是否登录，登录就放行
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Cont.LOGIN_USER);
        if (user != null){
            return true;
        }else {
            response.sendRedirect(request.getContextPath() +"/login.htm");
            return false; //拦截器断开，后面的拦截器链不再执行
        }

    }
}
