package com.ai.chat.controller;

import com.ai.chat.mapper.MsgMapper;
import com.ai.chat.mapper.UserLogMapper;
import com.ai.chat.pojo.Admin;
import com.ai.chat.pojo.Message;
import com.ai.chat.pojo.User;
import com.ai.chat.pojo.UserLog;
import com.ai.chat.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author aoufgo
 * @date 2021/4/5 下午4:35
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "adminServiceImpl")
    AdminService adminService;
    @Resource
    private MsgMapper msgMapper;
    @Resource
    private UserLogMapper userLogMapper;

    String rsKey = "result";

    /**
     * 查询用户
     *
     * @param user
     * @return
     */
    @RequestMapping("queryUser")
    public ModelAndView queryUser(User user) {
        ModelAndView mav = adminService.queryUser(user);
        mav.setViewName("admin/userManage");
        return mav;
    }

    /**
     * @param id 用户id
     * @return 返回到页面该用户
     */
    @GetMapping("getUser")
    public ModelAndView getUser(String id) {
        ModelAndView mav = adminService.getUser(id);
        mav.setViewName("admin/userEdit");
        return mav;
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    public ModelAndView updateUser(User user) {
        ModelAndView mav = adminService.updateUser(user);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 7);
        } else {
            mav.addObject("type", 6);
        }
        mav.setViewName("redirect:/admin/queryUser");
        return mav;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("delUser")
    public ModelAndView delUser(String id) {
        ModelAndView mav = adminService.delUser(id);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 5);
        } else {
            mav.addObject("type", 4);
        }
        mav.setViewName("redirect:/admin/queryUser");
        return mav;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("addUser")
    public ModelAndView addUser(User user) {
        ModelAndView mav = adminService.addUser(user);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 1);
        } else {
            mav.addObject("type", 2);
        }
        mav.setViewName("admin/userAdd");
        return mav;
    }

    /**
     * 管理员登录方法
     *
     * @param admin
     * @param session
     * @return
     */
    @RequestMapping("login")
    public ModelAndView adminLogin(Admin admin, HttpSession session) {
        /**
         * 根据名字更新登录时间    未完成***************
         */
        ModelAndView mav = adminService.login(admin, session);
        Admin admin1 = (Admin) session.getAttribute("admin");
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            System.out.println(admin1.getRoleId() + "------");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            admin1.setAdminLoginTime(sdf.format(new Date()));
            adminService.updateLoginTime(admin1);
            mav.addObject("type", 9);
        } else {
            mav.addObject("type", 8);
        }
        mav.setViewName("admin/adminLogin");
        return mav;
    }

    @RequestMapping("queryAdmin1")
    public ModelAndView queryAdmin1(Admin admin) {
        ModelAndView mav = adminService.queryAdmin1(admin);
        mav.setViewName("admin/adminManage");
        return mav;
    }


    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    @RequestMapping("delAdmin")
    public ModelAndView delAdmin(int adminId) {
        ModelAndView mav = adminService.del(adminId);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 5);
        } else {
            mav.addObject("type", 4);
        }
        mav.setViewName("redirect:/admin/queryAdmin1");
        return mav;

    }

    /**
     * 管理员Id
     *
     * @param adminId
     * @return
     */
    @GetMapping("getAdmin")
    public ModelAndView getAdmin(int adminId) {
        ModelAndView mav = adminService.queryAdminId(adminId);
        mav.setViewName("admin/adminEdit");
        return mav;
    }

    /**
     * 编辑管理员
     *
     * @param admin
     * @return
     */
    @RequestMapping("updateAdmin")
    public ModelAndView updateAdmin(Admin admin) {
        ModelAndView mav = adminService.updateAdmin(admin);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 7);
        } else {
            mav.addObject("type", 6);
        }
        mav.setViewName("redirect:/admin/queryAdmin1");
        return mav;

    }

    @RequestMapping("insertAdmin")
    public ModelAndView insertAdmin(Admin admin) {
        admin.setStatus(200);
        admin.setRoleId(2);
        ModelAndView mav = adminService.register(admin);
        rsKey = "result";
        if ((Boolean) mav.getModel().get(rsKey)) {
            mav.addObject("type", 1);
        } else {
            mav.addObject("type", 2);
        }
        mav.setViewName("redirect:/admin/queryAdmin1");
        return mav;
    }

    @RequestMapping("getMsg")
    public ModelAndView getMsg() {
        List<Message> msgs = msgMapper.query(new Message());
        ModelAndView mav = new ModelAndView();
        mav.addObject("msgs", msgs);
        mav.setViewName("admin/msgList");
        return mav;
    }
    @RequestMapping("getUserLog")
    public ModelAndView getUserLog() {
        List<UserLog> logs = userLogMapper.queryAllLog();
        ModelAndView mav = new ModelAndView();
        mav.addObject("logs", logs);
        mav.setViewName("admin/userLogList");
        return mav;
    }
    @RequestMapping("quit")
    public void quit(HttpSession session) {
        session.removeAttribute("admin");
    }
}
