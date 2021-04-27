package com.ai.chat.controller;

import com.ai.chat.pojo.Message;
import com.ai.chat.pojo.Relation;
import com.ai.chat.pojo.User;
import com.ai.chat.service.MsgService;
import com.ai.chat.service.UserService;
import com.ai.chat.util.SendCodeUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author aoufgo
 * @date 2021/4/2 下午4:52
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService service;
    @Resource(name = "msgServiceImpl")
    private MsgService msgService;
    String rsKey = "result";

    /**
     * 注册controller
     *
     * @param user 前端user
     * @return 反馈信息
     */
    @RequestMapping("register")
    public ModelAndView register(User user,@RequestParam String code,HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String code1 = (String) session.getAttribute("code");
        System.out.println("验证码:"+code1);
        if (code1 == null || !code1.equals(code)) {
            mav.addObject("type", 2);
            mav.setViewName("register");
        }else {
            ModelAndView mav1 = service.register(user);
            rsKey = "result";
            if ((Boolean) mav1.getModel().get(rsKey)) {
                mav.addObject("type", 1);
                mav.setViewName("login");
            } else {
                mav.addObject("type", 0);
                mav.setViewName("register");
            }
        }
        return mav;
    }

    /**
     * 登录controller
     *
     * @param user    前端user
     * @param session 存入session
     * @return 反馈信息
     */
    @RequestMapping("login")
    public ModelAndView login(User user, HttpSession session) {
        ModelAndView mav = service.login(user, session);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 4);
        } else {
            mav.addObject("type", 7);
        }
        mav.setViewName("login");
        return mav;
    }
    @RequestMapping("codeLogin")
    public ModelAndView codeLogin(@RequestParam String code, @RequestParam String phone,HttpSession session) {
        return service.codeLogin(code,phone,session);
    }

    /**
     * 跳转到chat页面
     *
     * @param id
     * @return
     */
    @GetMapping("jumpToChat/{id}")
    public ModelAndView chat(@PathVariable String id) {
        ModelAndView mav = service.chat(id);
        //获取好友邀请信息
        mav.addObject("reqList", msgService.getReq(id));
        mav.setViewName("chat");
        return mav;
    }

    @GetMapping("getFriends/{id}")
    public void getFriends(@PathVariable String id, HttpServletRequest request) {
        ModelAndView mav = service.chat(id);
        List<Relation> list = (List<Relation>) mav.getModel().get("friends");
        System.out.println(list);
        request.setAttribute("friends", list);
    }

    @GetMapping("link/{fromId}/{toId}")
    public ModelAndView linkTo(@PathVariable String fromId, @PathVariable String toId) {
        //查询聊天记录
        ModelAndView mav = msgService.getChatLog(fromId, toId);
        mav.addObject("fromId", fromId);
        mav.addObject("id", toId);
        //获取对象头像
        mav.addObject("toIdAvatar",service.getInfo(toId).getAvatarUrl());
        mav.setViewName("chat_page");
        return mav;
    }

    @RequestMapping("quit")
    public void quit(HttpSession session) {
        session.removeAttribute("user");
    }

    @RequestMapping("isRead/{fromId}/{toId}")
    public void updateRead(@PathVariable String fromId, @PathVariable String toId) {
        msgService.updateRead(fromId, toId);
    }

    @RequestMapping("getUnread/{id}")
    public String getUnread(@PathVariable String id) {
        return JSON.toJSONString(msgService.getUnread(id));
    }

    @RequestMapping("addF/{id1}/{id2}")
    public String quit(@PathVariable String id1, @PathVariable String id2) {
        return (service.addF(id1, id2) ? "yes" : "no");
    }

    /**
     * 判断是否存在用户
     *
     * @param id 用户id
     * @return 结果
     */
    @RequestMapping("getUser/{id}")
    public String getUser(@PathVariable String id) {
        return (service.getUser(id) ? "yes" : "no");
    }
    @RequestMapping("getUserByPhone/{phone}")
    public String getUserByPhone(@PathVariable String phone) {
        return (service.getUserByPhone(phone) ? "yes" : "no");
    }

    @RequestMapping("getInfo/{id}")
    public String getInfo(@PathVariable String id) {
        User u = service.getInfo(id);
        return JSON.toJSONString(u);
    }

    @RequestMapping("update")
    public String update(User user) {
        return (service.update(user) ? "yes" : "no");
    }

    @RequestMapping("uploadAvatar")
    public String uploadAvatar(@RequestParam(value = "avatar") MultipartFile file, HttpServletRequest req) throws IOException {
        // 判断文件是否为空，空则返回失败页面
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径
            String filePath = "/Volumes/MacData/chatAvatar/";
            // 用uuid给新文件命名
            String fileUUName = UUID.randomUUID().toString();
            String path = filePath + fileUUName + suffixName;
            System.out.println(path);
            // 创建一个新文件
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            map.put("result", "上传成功");
            map.put("name", fileUUName + suffixName);
        }
        return JSON.toJSONString(map);
    }
    @RequestMapping("sendCode")
    public String sendCode(@RequestParam String userPhone,HttpSession session ) throws IOException {
        //调用发送验证码的方法
        String code = SendCodeUtil.send(userPhone);
        //创建json对象
        JsonObject json = new JsonObject();
        if (code == null) {
            System.out.println("发送失败");
            //作出响应  json数据
            //将响应的结果添加到json对象中
            json.addProperty("result", "no");
        } else {
            System.out.println("发送成功");
            //设置验证码的有效期
            //将服务器中的验证码存储起来   session
            session.setAttribute("code", code);
            //当过了有效期，将存储的验证码删除
            //在固定时间后，删除session中的验证码  Timer
            //创建计时器
            Timer timer = new Timer();
            //执行计时器五分钟后删除session的验证码
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //删除session中的验证码
                    session.removeAttribute("code");
                    System.out.println("验证码已失效");
                    //取消计时器
                    timer.cancel();
                }
            }, 1000 * 60 * 5);
            json.addProperty("result", "yes");
        }
        return json.toString();
    }
    @RequestMapping("checkName")
    public String checkName(@RequestParam String name){
        return (service.checkName(name) ? "yes" : "no");
    }

}
