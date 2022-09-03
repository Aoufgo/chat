package com.ai.chat.service.impl;

import com.ai.chat.mapper.AdminMapper;
import com.ai.chat.mapper.UserMapper;
import com.ai.chat.pojo.Admin;
import com.ai.chat.pojo.User;
import com.ai.chat.service.AdminService;
import com.ai.chat.util.GenerateIdUtil;
import com.ai.chat.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author aoufgo
 * @date 2021/4/5 下午4:28
 */
@Service
public class AdminServiceImpl implements AdminService{
    ModelAndView mav = new ModelAndView();
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;

    /**
     * 查询用户信息
     * @param user 条件
     * @return
     */
    @Override
    public ModelAndView queryUser(User user) {
        try{
            List<User> users = userMapper.query(user);
            mav.addObject("users", users);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 获取用户id
     * @param id 用户id
     * @return
     */
    @Override
    public ModelAndView getUser(String id) {
        mav.addObject("user",userMapper.queryById(id));
        return  mav;
    }

    /**
     * 获取管理员Id
     * @param adminId
     * @return
     */
    @Override
    public ModelAndView getAdmin(int adminId) {
        mav.addObject("admin",adminMapper.queryById(adminId));
        return mav;
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return
     */
    @Override
    public ModelAndView delUser(String id) {
        try {
            userMapper.del(id);
            mav.addObject("result",true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 编辑用户信息
     * @param user 更新过的用户信息
     * @return
     */
    @Override
    public ModelAndView updateUser(User user) {
        try {
            userMapper.update(user);
            mav.addObject("result",true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 添加用户
     * @param user 用户的信息
     * @return
     */
    @Override
    public ModelAndView addUser(User user) {
        try {
            //随机生成id
            user.setId(GenerateIdUtil.generateId());
            String id = user.getId();
            //设置用户昵称
            user.setNickname("用户"+id);
            //对密码Md5加密
            String password = Md5Util.md5(user.getPassword());
            user.setPassword(password);
            userMapper.add(user);
            mav.addObject("result",true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 管理员登录
     * @param admin
     * @param session
     * @return
     */
    @Override
    public ModelAndView login(Admin admin, HttpSession session) {
        try {
            String adminPassword = Md5Util.md5(admin.getAdminPassword());
            admin.setAdminPassword(adminPassword);
            if (adminMapper.queryByNp(admin) == null) {
                mav.addObject("result", false);
            } else {
                admin = adminMapper.queryByNp(admin);
                session.setAttribute("admin", admin);
                mav.addObject("result", true);
            }
        }catch (Exception e){
//            mav.addObject("error",e.getCause().toString());
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public ModelAndView register(Admin admin) {
        try {
            String adminPassword = Md5Util.md5(admin.getAdminPassword());
            admin.setAdminPassword(adminPassword);
            adminMapper.insertAdmin(admin);
            mav.addObject("result", true);
        }catch (Exception e){
            mav.addObject("error", e.getCause().toString());
            mav.addObject("result", false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 查询管理员
     * @param admin
     * @return
     */

    @Override
    public ModelAndView queryAdmin1(Admin admin) {
        System.out.println(admin);
        try{
            List<Admin> admins = adminMapper.query(admin);
            mav.addObject("admins",admins);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            e.printStackTrace();
        }
        return mav;
    }




    /**
     * 根据ID删除管理员
     * @param adminId
     * @return
     */
    @Override
    public ModelAndView del(int adminId) {
        try {
            adminMapper.delAdmin(adminId);
            mav.addObject("result", true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());

            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 查询管理员
     * @param adminId
     * @return
     */
    @Override
    public ModelAndView queryAdminId(int adminId) {
        mav.addObject("admin",adminMapper.queryById(adminId));
        return mav;
    }

    @Override
    public Admin queryByAdminName(Admin admin) {
        return adminMapper.queryByAdminName(admin);
    }

    /**
     * 编辑发送管理员
     * @param admin
     * @return
     */
    @Override
    public ModelAndView updateAdmin(Admin admin) {
        try {
            adminMapper.update(admin);
            mav.addObject("result",true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 根据名字更新登录时间
     * @param admin
     * @return
     */
    @Override
    public ModelAndView updateLoginTime(Admin admin) {
        try {
            adminMapper.updateByAdminName(admin);
            mav.addObject("result",true);
        }catch (Exception e){
            mav.addObject("error",e.getCause().toString());
            mav.addObject("result",false);
            e.printStackTrace();
        }
        return mav;
    }
}
