package com.ai.chat.service.impl;


import com.ai.chat.mapper.RelaMapper;
import com.ai.chat.pojo.Relation;
import com.ai.chat.service.UserService;
import com.ai.chat.mapper.UserMapper;
import com.ai.chat.pojo.User;
import com.ai.chat.util.GenerateIdUtil;
import com.ai.chat.util.Md5Util;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author aoufgo
 */
@Service

public class UserServiceImpl implements UserService {
    @Resource(name = "userMapper")
    private UserMapper mapper;
    @Resource
    private RelaMapper relaMapper;
    ModelAndView mav = new ModelAndView();


    @Override
    public ModelAndView register(User user) {
        try {
            //随机生成id
            user.setId(GenerateIdUtil.generateId());
            //对密码Md5加密
            String password = Md5Util.md5(user.getPassword());
            user.setPassword(password);
            //添加数据库
            mapper.add(user);
            mav.addObject("result", true);

        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            mav.addObject("result", false);
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public ModelAndView login(User user, HttpSession session) {
        try {
            //md5加密
            String password = Md5Util.md5(user.getPassword());
            user.setPassword(password);
            if (mapper.queryByNP(user) == null) {
                mav.addObject("result", false);
            } else {
                //将user对象存入session域对象
                user = mapper.queryById(user.getId());
                session.setAttribute("user", user);
                mav.addObject("result", true);
            }
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public ModelAndView codeLogin(String code, String phone, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            //校验验证码
            String code1 = (String) session.getAttribute("code");
            if (code1 == null || !code1.equals(code)) {
                //验证码不正确
                mav.addObject("type", 5);
            } else {
                //查询是否有该手机号
                User user = mapper.queryByPhone(phone);
                if (user == null) {
                    //手机号错误
                    mav.addObject("type", 2);
                } else {
                    //登录成功
                    //将user对象存入session域对象
                    user = mapper.queryById(user.getId());
                    session.setAttribute("user", user);
                    mav.addObject("type", 4);
                }

            }
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            e.printStackTrace();
        }
        mav.setViewName("login");
        return mav;
    }

    @Override
    public ModelAndView chat(String id) {
        try {
            //获取id的好友和分组
            List<Relation> friends = mapper.getFriends(id);
            mav.addObject("friends", friends);
        } catch (Exception e) {
            mav.addObject("error", e.getCause().toString());
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public Boolean addF(String id1, String id2) {
        return relaMapper.addF(id1, id2) > 0;
    }

    @Override
    public Boolean getUser(String id) {
        return mapper.queryById(id) != null;
    }

    @Override
    public Boolean getUserByPhone(String phone) {
        return mapper.queryByPhone(phone)!=null;
    }

    @Override
    public Boolean checkName(String name) {
        return mapper.queryByName(name) != null;
    }

    @Override
    public User getInfo(String id) {
        return mapper.queryById(id);
    }

    @Override
    public Boolean update(User user) {

        return mapper.update(user) > 0;
    }


}
