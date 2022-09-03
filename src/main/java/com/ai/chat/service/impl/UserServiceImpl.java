package com.ai.chat.service.impl;


import com.ai.chat.mapper.RelaMapper;
import com.ai.chat.pojo.Relation;
import com.ai.chat.service.UserService;
import com.ai.chat.mapper.UserMapper;
import com.ai.chat.pojo.User;
import com.ai.chat.util.FaceUtil;
import com.ai.chat.util.GenerateIdUtil;
import com.ai.chat.util.Md5Util;
import com.ai.chat.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

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
    @Resource
    private RedisUtil redisUtil;


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
            mav.addObject("id",user.getId());
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
            user = mapper.queryByNP(user);
            if (user == null) {
                mav.addObject("result", false);
            } else {
                // 保存信息
                String token =  saveLoginUserInfo(user,session);
                mav.addObject("token",token);
                mav.addObject("result", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @Override
    public ModelAndView codeLogin(String code, String phone, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            //查询是否有该手机号
            User user = mapper.queryByPhone(phone);
            if (user == null) {
                //手机号错误
                mav.addObject("type", 2);
            } else {
                // 获取验证码
                String code1 = redisUtil.getVerificationCode(user.getId());
                if (code1 == null || !code1.equals(code)) {
                    //验证码不正确
                    mav.addObject("type", 5);
                } else {
                    //登录成功
                    // 保存信息
                    String token =  saveLoginUserInfo(user,session);
                    mav.addObject("token",token);
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
    public String faceLogin(String img, HttpSession session) {
        //查询到所有的用户
        List<User> list = mapper.query(new User());
        //拿要登录的人脸信息与每一个用户的人脸对比
        //假设登陆失败
        boolean checkFace = false;
        String token = "";
        //遍历每一个人用户信息
        for (User u : list) {
            String facePath = "/Volumes/mac-data/faceImgs/" + u.getName() + ".jpg";
            //拿要登录的人脸信息与每一个用户的人脸对比
            if (u.getAccStatus() == 200 && u.getFaceUrl() != null && !u.getFaceUrl().equals("")) {
                //拿登录的人脸与每一个用户的人脸对比，看是否为同一个人
                checkFace = FaceUtil.checkFace(img, facePath);
                if (checkFace) {
                    //如果结果为true
                    // 保存信息
                    token = saveLoginUserInfo(u,session);
                    break;
                }
            }
        }
        //响应结果
        JsonObject jsonObject = new JsonObject();
        if (checkFace) {
            jsonObject.addProperty("msg", "登录成功");
            jsonObject.addProperty("token", token);
        } else {
            jsonObject.addProperty("msg", "没有查询到人脸信息，或账号禁止登录");
        }
        return jsonObject.toString();
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
    public User getUserByPhone(String phone) {
        return mapper.queryByPhone(phone);
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
        //密码MD5加密
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(Md5Util.md5(user.getPassword()));
        }
        return mapper.update(user) > 0;
    }

    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径
            String filePath = "/Volumes/mac-data/chatAvatar/";
            // 用uuid给新文件命名
            String fileUUName = UUID.randomUUID().toString();
            String path = filePath + fileUUName + suffixName;
            System.out.println(path);
            // 创建一个新文件
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            map.put("result", "上传成功");
            map.put("name", fileUUName + suffixName);
        }
        return JSON.toJSONString(map);
    }

    @Override
    public String faceRegister(String img, HttpSession session) {
        JsonObject jsonObject = new JsonObject();
        try {
            String facePath = "/Volumes/mac-data/faceImgs/";
            //根据要存储的目录file对象
            File file = new File(facePath);
            //检查目录是否存在
            if (!file.exists()) {
                //如果目录不存在，创建出目录
                file.mkdirs();
            }
            //获取登录对象
            User user = (User) session.getAttribute("user");
            facePath += user.getName() + ".jpg";
            //图片的访问路径
            String faceUrl = "/faceImgs/" + user.getName() + ".jpg";
            //将img通过base64解码，还原为原始二进制形式
            byte[] imgByte = Base64.decodeBase64(img);
            //创建输出流，将图片信息写出
            OutputStream out = null;
            out = new FileOutputStream(facePath);
            //创建输入读取imgByte图片信息
            InputStream in = new ByteArrayInputStream(imgByte);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                //将读取到的内容写出
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            //将faceUrl以及facePath存储到数据库中
            User u = new User();
            u.setId(user.getId());
            u.setFaceUrl(faceUrl);
            mapper.update(u);
            jsonObject.addProperty("msg", "注册成功");
        } catch (Exception e) {
            jsonObject.addProperty("msg", "注册失败");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public String changePW( User user, String code,HttpSession session) {
        //校验验证码
        String code1 = (String) session.getAttribute("code");
        if (code1 == null || !code1.equals(code)) {
            return "验证码不正确";
        } else {
            //查询是否有该手机号
            if (mapper.queryByPhone(user.getPhone()) == null) {
                return "手机号错误";
            } else {
                //修改密码
                user.setPassword(Md5Util.md5(user.getPassword()));
                return mapper.update(user) > 0 ? "修改成功" : "修改失败";
            }
        }
    }

    private String saveLoginUserInfo(User user,HttpSession session){
        // 缓存token
        String token = redisUtil.cacheToken(user.getId());
        // 更新时间
        user.setLastLoginTime(System.currentTimeMillis() + "");
        mapper.update(user);
        //将user对象存入session域对象
        session.setAttribute("user", user);
        return token;
    }
}



