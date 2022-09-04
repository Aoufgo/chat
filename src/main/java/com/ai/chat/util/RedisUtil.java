package com.ai.chat.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.ai.chat.mapper.MsgMapper;
import com.ai.chat.pojo.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    @Resource
    MsgMapper msgMapper;

    public static final String NAMESPACE = "CHAT:";

    public static final String MSG = "MSG:";

    public static final String USER = "USER:";

    public static final String VERIFICATION_CODE = "verificationCode";

    public static final String USER_TOKEN = "userToken:";

    public static final String UNREAD = "_UNREAD";

    // 用户记录过期时间进行回调
    public static final String EX = "EX:";



    public void addMsg(Message message) {
        // 获取请求用户
        String fromId = message.getFromId();
        // 获取接受用户
        String toId = message.getToId();
        // 获取时间
        String sendTime = message.getSendTime();
        // 将对象转为json字符串
        String json = JSONUtil.toJsonStr(message);
        // 设置缓存
        redisTemplate.opsForValue().set(NAMESPACE + MSG + fromId + ":" + toId + ":" + sendTime + UNREAD, json);
        // 设置记录时间的缓存
        redisTemplate.opsForValue().set(NAMESPACE + EX + fromId + ":" + toId + ":" + sendTime, "", 7L, TimeUnit.DAYS);
    }

    // 将redis持久化到数据库并删除缓存
    public void syncMsg(String key){
        Boolean isRead = redisTemplate.hasKey(key);
        if(isRead != null && !isRead){
            key = key + UNREAD;
        }
        // 获取json
        String json = (String) redisTemplate.opsForValue().get(key);
        // 转为对象
        com.ai.chat.pojo.Message msg = JSONUtil.toBean(json, com.ai.chat.pojo.Message.class);
        if(isRead != null && isRead){
            msg.setIsRead(1);
        }
        // 存入数据库
        msgMapper.add(msg);
        // 删除缓存
        redisTemplate.delete(key);
    }

    public List<Message> getMessages(String fromId,String toId){
        String key = NAMESPACE + MSG + fromId + ":" + toId + ":*";
        String key1 = NAMESPACE + MSG + toId + ":" + fromId + ":*";
        Set<String> keys = redisTemplate.keys(key);
        Set<String> keys1 = redisTemplate.keys(key1);
        if (keys == null || keys1 == null) {
            return new ArrayList<>();
        }
        keys.addAll(keys1);
        // 将key排序
        TreeSet<String> keySet = new TreeSet<>(((s1,s2)->{
            String time1 = s1.substring(s1.lastIndexOf(":"));
            String time2 = s2.substring(s1.lastIndexOf(":"));
            return time1.compareTo(time2);
        }));
        keySet.addAll(keys);
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean notFirst = false;
        for (String s : keySet) {
            if (notFirst) {
                json.append(",");
            }
            // 获取缓存
            String msg = (String) redisTemplate.opsForValue().get(s);
            // 如果未读消息
            if (!s.endsWith(UNREAD) && msg != null){
                msg = msg.replace("\"isRead\":0", "\"isRead\":1");
            }
            json.append(msg);
            notFirst = true;
        }
        json.append("]");
        // 转为list
        JSONArray array = JSONUtil.parseArray(json);
        return array.toList(Message.class);
    }


    // 保存验证码方法
    public void cacheCode (String userId, String code){
        String key = NAMESPACE + USER + userId + ":" + VERIFICATION_CODE;
        redisTemplate.opsForValue().set(key, code , 5L ,TimeUnit.MINUTES);
    }

    // 获取验证码的方法
    public String getVerificationCode(String userId){
        String key = NAMESPACE + USER + userId + ":" + VERIFICATION_CODE;
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 缓存token
    public String cacheToken(String userId){
        String token = UUID.randomUUID().toString();
        String key = NAMESPACE + USER_TOKEN + token;
        redisTemplate.opsForValue().set(key, userId , 30L ,TimeUnit.MINUTES);
        return token;
    }

    // 根据token获取用户id
    public String geToken(String token){
        String key = NAMESPACE + USER_TOKEN + token;
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 将消息设为已读
    public void setRead(String fromId,String toId){
        String unreadkey = NAMESPACE + MSG + fromId + ":" + toId + ":*"+ UNREAD;
        Set<String> keys = redisTemplate.keys(unreadkey);
        if (keys == null){
            return;
        }
        for (String key : keys) {
            redisTemplate.rename(key,key.replace(UNREAD,""));
        }
    }
    // 获取未读消息
    public List<Map<String, String>> getUnread(String id){
        String key = NAMESPACE + MSG + "*" + ":" + id + ":*"+ UNREAD;
        Set<String> keys = redisTemplate.keys(key);
        Map<String,Integer> map = new HashMap<>(16);
        for (String k : keys) {
            // 获取fromID
            String fromId = k.substring(k.indexOf(MSG) + 4,k.indexOf(id) - 1);
            if (map.containsKey(fromId)){
                map.put(fromId,map.get(fromId) + 1);
            } else {
                map.put(fromId, 1);
            }
        }
        List<Map<String, String>> list = new ArrayList<>();
        map.forEach((fromId,count)->{
            Map<String,String> map1 = new HashMap<>(2);
            map1.put("fromId",fromId);
            map1.put("count",count.toString());
            list.add(map1);
        });
        return list;

    }

}
