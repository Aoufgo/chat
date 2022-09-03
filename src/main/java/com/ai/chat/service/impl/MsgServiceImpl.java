package com.ai.chat.service.impl;

import com.ai.chat.mapper.MsgMapper;
import com.ai.chat.pojo.Message;
import com.ai.chat.service.MsgService;
import com.ai.chat.util.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author guoao
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Resource(name = "msgMapper")
    private MsgMapper mapper;

    @Resource
    private RedisUtil redisUtil;

    ModelAndView mav = new ModelAndView();

    @Override
    public ModelAndView add(Message msg) {
        try {
            // 将消息存到redis中
            redisUtil.addMsg(msg);
//            mapper.add(msg);
            mav.addObject("result",true);
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public ModelAndView query(Message msg) {
        try {
            List<Message> messages = mapper.query(msg);
            mav.addObject("messages",messages);
            mav.addObject("result",true);
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public ModelAndView getChatLog(String fromId, String toId) {
        try {
//            List<Message> messages = mapper.getChatLog(fromId,toId);
            List<Message> messages = redisUtil.getMessages(fromId, toId);
            mav.addObject("messages",messages);
            mav.addObject("result",true);
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public void updateRead(String fromId, String toId) {
        // 设置缓存中已读
        redisUtil.setRead(fromId,toId);
        // 设置数据库已读
        mapper.updateRead(fromId, toId);
    }

    @Override
    public List<Map<String, String>> getUnread(String id) {
        return redisUtil.getUnread(id);
//        return mapper.getUnread(id);
    }
    @Override
    public List<Message> getReq(String id) {
        return mapper.getReq(id);
    }
}
