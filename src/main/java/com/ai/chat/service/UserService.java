package com.ai.chat.service;

import com.ai.chat.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author aoufgo
 * @date 2021/4/2 下午4:32
 */
@Service
public interface UserService {
    /**
     * 注册用户
     * @param user 接收的user对象
     * @return 页面信息
     */
    ModelAndView register(User user);
    /**
     * 登录用户
     * @param user 接收的user对象
     * @param session session域对象
     * @return 页面信息
     */
    ModelAndView login(User user, HttpSession session);

    /**
     * 跳转到聊天页面
     * @param id 用户id
     * @return mav
     */
    ModelAndView chat(String id);

    /**
     * 添加好友关系
     * @param id1 id1
     * @param id2 id2
     * @return 添加成功
     */
    Boolean addF(String id1,String id2);
    /**
     * 获取用户信息
     * @param id id
     * @return 是否有该用户
     */
    Boolean getUser(String id);
    /**
     * 用户信息
     * @param id id
     * @return 对象
     */
    User getInfo(String id);

    /**
     * 修改用户信息
     * @param user 对象
     * @return 是否修改成功
     */
    Boolean update(User user);





}
