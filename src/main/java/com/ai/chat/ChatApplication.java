package com.ai.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

/**
 * @author aoufgo
 */
@ServletComponentScan   //扫描Servlet
@MapperScan("com.ai.chat.mapper")  //自动扫描mapper文件
@EnableAspectJAutoProxy//自动扫描所有的aspectJ代理,spring来生成代理对象

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ChatApplication.class, args);
        Environment environment = run.getEnvironment();
        String port = environment.getProperty("server.port");
        String context = environment.getProperty("server.servlet.context-path");
        String ip = "localhost";
        String systemIndexUrl = "http://" + ip + ":" + port + context;
        String managerUrl = systemIndexUrl + "/admin_page/adminLogin";
        System.out.printf(">>>>>>>>>>>>>>>>>>>>项目主页地址：%s >>>>>>>>>>>>>>>>>>>>\n", systemIndexUrl);
        System.out.printf(">>>>>>>>>>>>>>>>>>>>后台管理地址：%s >>>>>>>>>>>>>>>>>>>>\n", managerUrl);
    }

}
