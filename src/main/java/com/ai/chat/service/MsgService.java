package com.ai.chat.service;

import com.ai.chat.pojo.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author guoao
 */
public interface MsgService {
    /**
     * 添加信息记录
     * @param message 信息对象
     * @return 影响行数
     */
    ModelAndView add(Message message);

    /**
     * 查询信息记录
     * @param message 信息对象
     * @return 信息集合
     */
    ModelAndView query(Message message);

    /**
     * 查询信息往来记录
     * @param fromId 发送者id
     * @param toId 接收者id
     * @return 信息集合
     */
    ModelAndView getChatLog(String fromId,String toId);

    /**
     * 将对方的信息设为已读
     * @param fromId 发送者id
     * @param toId 接收者id
     */
    void updateRead(String fromId, String toId);

    /**
     * 获取用户的未读消息
     * @param id 用户id
     * @return 用户好友对应的未读消息数
     */
    List<Map<String,String>> getUnread(String id);
    /**
     * 获取用户的未读好友邀请
     * @param id 用户Id
     * @return 邀请信息
     */
    List<Message> getReq(String id);

}
