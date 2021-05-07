package com.ai.chat.Interceptor;

import com.ai.chat.pojo.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aoufgo
 * @date 2021/5/6 上午8:56
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在controller执行之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("请求路径"+uri);
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin != null&&admin.getStatus()==200){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/admin_page/adminLogin");
        return false;
    }
}
