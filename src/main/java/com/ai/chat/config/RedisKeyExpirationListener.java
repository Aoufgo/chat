package com.ai.chat.config;


import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.ai.chat.mapper.MsgMapper;
import com.ai.chat.util.RedisUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String expiredKey = message.toString();
            if (expiredKey.startsWith(RedisUtil.NAMESPACE + RedisUtil.EX)) {
                Log.get().info("过期回调,过期的key:" + expiredKey);
                String key = expiredKey.replace(RedisUtil.EX, RedisUtil.MSG);
                // 同步数据
                redisUtil.syncMsg(key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
