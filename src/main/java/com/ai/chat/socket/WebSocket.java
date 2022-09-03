package com.ai.chat.socket;

import com.ai.chat.pojo.Message;
import com.ai.chat.pojo.User;
import com.ai.chat.service.MsgService;
import com.ai.chat.service.UserService;
import com.ai.chat.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aoufgo
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {

    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static final Map<String, WebSocket> CLIENTS = new ConcurrentHashMap<>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户id
     */
    private String userId;

    @Resource
    private RedisUtil redisUtil;


    private static UserService userServiceImpl;

    private static MsgService msgServiceImpl;

    @Resource
    public void setUserServiceImpl(UserService userServiceImpl) {
        WebSocket.userServiceImpl = userServiceImpl;
    }
    @Resource
    public void setMsgServiceImpl(MsgService msgServiceImpl) {
        WebSocket.msgServiceImpl = msgServiceImpl;
    }
    /*
     * OnOpen 表示有浏览器链接过来的时候被调用
     * OnClose 表示浏览器发出关闭请求的时候被调用
     * OnMessage 表示浏览器发消息的时候被调用
     * OnError 表示有错误发生，比如网络断开了等等
     */

    /**
     * 建立连接
     *
     * @param session HttpSession对象
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        //判断用户是否在线
        if (CLIENTS.get(userId)!=null){
            //向用户发送强制下线响应
            Map<String,String> map = new HashMap<>();
            map.put("action","forceOffline");
            System.out.println(map);
            sendMessageTo(JSON.toJSONString(map),userId);
        }
        //设置用户的在线状态为在线
        User user = new User();
        user.setStatus(200);
        user.setId(userId);
        userServiceImpl.update(user);
        try {
            //将所有人对自己修改在线状态
            Map<String, Object> map1 = new HashMap<>();
            map1.put("action","online");
            map1.put("id",userId);
            sendMessageAll(JSON.toJSONString(map1));
        } catch (IOException e) {
            log.info(userId+ "上线的时候发生了错误");
        }
        onlineNumber++;
        log.info("有新连接加入！ 当前在线人数" + onlineNumber);
        log.info("现在来连接的客户id：" + session.getId() + "用户Id：" + userId);
        this.userId = userId;
        this.session = session;
        CLIENTS.put(userId, this);
    }

    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        offline();
        log.info("页面无请求,自动下线:" + error);
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);

    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() throws IOException {
        offline();
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);

    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            log.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromId = jsonObject.getString("userId");
            String toId = jsonObject.getString("to");
            String time = jsonObject.getString("sendTime");
            String type = jsonObject.getString("type");
            //发送信息给
            Map<String, Object> map1 = new HashMap<>();
            if ("message".equals(type)) {
                map1.put("action", "send");
            } else if ("friendReq".equals(type)){
                map1.put("action", "friendReq");
                map1.put("time",time);
            }
            map1.put("textMessage", textMessage);
            map1.put("fromId", fromId);
            map1.put("toId", toId);
            sendMessageTo(JSON.toJSONString(map1),toId);
            //将信息存入数据库
            Message msg = new Message(fromId,toId,textMessage,time,0);
            msgServiceImpl.add(msg);
        } catch (Exception e) {
            log.info("发生了错误了:"+e);
            e.printStackTrace();
        }
    }

    private void offline()throws IOException {
        onlineNumber--;
        CLIENTS.remove(userId);
        //设置用户的在线状态为离线
        User user = new User();
        user.setStatus(100);
        user.setId(userId);
        userServiceImpl.update(user);
        //关闭session
        session.close();
        try {
            //给所有人自己修改在线状态为离线
            Map<String, Object> map1 = new HashMap<>();
            map1.put("action","offline");
            map1.put("id",userId);
            sendMessageAll(JSON.toJSONString(map1));
        } catch (IOException e) {
            log.info(userId+ "下线的时候通知所有人发生了错误");
        }
        // 同步缓存
//        redisUtil.syncMsgById(userId);
    }

    private void sendMessageTo(String message,String toId) throws IOException {
        for (WebSocket item : CLIENTS.values()) {
            if (item.userId.equals(toId)) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }
    private void sendMessageAll(String message) throws IOException {
        for (WebSocket item : CLIENTS.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }


}
