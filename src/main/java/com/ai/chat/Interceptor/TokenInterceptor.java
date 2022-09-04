package com.ai.chat.Interceptor;

import cn.hutool.json.JSONUtil;
import com.ai.chat.pojo.Admin;
import com.ai.chat.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("请求路径"+uri);
        // todo 实现token验证
        Map<Object, Object> map = new HashMap<>();
        String token = request.getParameter("token");
        if (token == null) {
            map.put("msg","token无效！");
        } else {
            try {
                String userId = redisUtil.geToken(token);
                request.setAttribute("userId", userId);
                map.put("state",true);
                map.put("msg","请求成功");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                map.put("msg","token无效！");
            }
        }
        String json = JSONUtil.toJsonStr(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

}
