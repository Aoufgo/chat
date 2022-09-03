package com.ai.chat.aspect;

import com.ai.chat.mapper.UserLogMapper;
import com.ai.chat.pojo.User;
import com.ai.chat.pojo.UserLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author aoufgo
 * @date 2021/4/8 下午11:11
 */
@Component
@Aspect //表示当前类是切面类
public class ServiceAspect {
    /*
     * 定义方法,实现切面功能
     * 1.public
     * 2.void
     * 3.名称自定义
     * 4.可以有参数,也可以无
     */

    @Resource
    private UserLogMapper userLogMapper;
    /**
     * 在所有service方法执行前获得时间
     */
    @Before(value = "execution(* *..*Impl.*(..))")
    public void timeBefore(JoinPoint jp){
        //获取方法的定义
        System.out.println("方法的名称:"+jp.getSignature().getName());
        System.out.println("业务执行的时间为"+new Date());
    }

    /**通过returning属性指定连接点方法返回的结果放置在result变量中，在返回通知方法中可以从result变量中获取连接点方法的返回结果了。*/
    @AfterReturning(value="execution(* *..UserServiceImpl.*(com.ai.chat.pojo.User))",
            returning="result")
    public void addUserLog(JoinPoint point, Object result){
        String methodName = point.getSignature().getName();
        User user = (User) point.getArgs()[0];
        userLogMapper.add(new UserLog(0,user.getId(),methodName,null));
    }
//    @AfterReturning(value="execution(* *..UserServiceImpl.*(..,javax..HttpSession,..))",
//            returning="result")
//    public void addUserLoginLog(JoinPoint point, Object result){
//        String methodName = point.getSignature().getName();
//        int length = point.getArgs().length;
//        HttpSession session = (HttpSession) point.getArgs()[length-1];
//        User user = (User)session.getAttribute("user");
//        userLogMapper.add(new UserLog(0,user.getId(),methodName,null));
//    }
}
