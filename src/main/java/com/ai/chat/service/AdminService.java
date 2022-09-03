package com.ai.chat.service;

import com.ai.chat.pojo.Admin;
import com.ai.chat.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * @author aoufgo
 * @date 2021/4/5 下午4:28
 */
public interface AdminService {

    /**
     * 条件查询用户
     * @return mav
     * @param user 条件
     */
    ModelAndView queryUser(User user);

    /**
     * 获取用户
     * @param id 用户id
     * @return mav
     */
    ModelAndView getUser(String id);

    /**
     * 获取管理员
     * @param adminId
     * @return
     */
    ModelAndView getAdmin(int adminId);
    /**
     * 删除用户方法
     * @param id 用户id
     * @return mav
     */
    ModelAndView delUser(String id);

    /**
     * 更新用户信息方法
     * @param user 更新过的用户信息
     * @return mav
     */
    ModelAndView updateUser(User user);

    /**
     * 添加用户信息方法
     * @param user 用户的信息
     * @return mav
     */
    ModelAndView addUser(User user);

    /**
     * 管理员登录
     * @param admin
     * @param session
     * @return
     */
    ModelAndView login(Admin admin, HttpSession session);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    ModelAndView register(Admin admin);


    ModelAndView queryAdmin1(Admin admin);

    /**
     * 删除管理员
     * @param adminId
     * @return
     */
    ModelAndView del(int adminId);

    /**
     * 查询管理员ID
     * @param adminId
     * @return
     */
    ModelAndView queryAdminId(int adminId);

    Admin queryByAdminName(Admin admin);

    /**
     * 修改管理员
     * @param admin
     * @return
     */
    ModelAndView updateAdmin(Admin admin);

    /**
     * 根据名字更新登录时间  未完成  ********
     * @param admin
     * @return
     */
    ModelAndView updateLoginTime(Admin admin);
}
