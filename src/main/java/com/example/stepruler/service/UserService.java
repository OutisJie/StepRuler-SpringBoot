package com.example.stepruler.service;

import com.example.stepruler.Entity.FriendEntity;
import com.example.stepruler.Entity.UserEntity;
import com.example.stepruler.builder.MD5Util;
import com.example.stepruler.jpa.FriendJPA;
import com.example.stepruler.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserJPA userJPA;

    @Autowired
    FriendJPA friendJPA;


    //注册
    public String register(UserEntity entity) {
        List<UserEntity> users = userJPA.findAll();
        for (UserEntity user : users) {
            if (entity.getUserName().equals(user.getUserName())) {
                return "failed";
            }
        }
        UserEntity nUserEntity = new UserEntity();
        nUserEntity.setUserName(entity.getUserName());
        nUserEntity.setUserPwd(MD5Util.encode(entity.getUserPwd()));
        userJPA.save(nUserEntity);
        return "success";
    }

    //登录
    public int login(String name, String pwd) {
        String entity_pwd = MD5Util.encode(pwd);
        List<UserEntity> users = userJPA.findAll();
        for (UserEntity user : users) {
            if (name.equals(user.getUserName()) && entity_pwd.equals(user.getUserPwd())) {
                return user.getUserId();
            }
            if (name.equals(user.getUserName()) && !entity_pwd.equals(user.getUserPwd())) {
                return -2;
            }
        }
        return -1;
    }

    //搜索
    public ArrayList<String> search(String name) {
        ArrayList<String> names = new ArrayList<>();
        if (name == "") {
            names.add("没有查询到该数据");
            return names;
        }
        List<UserEntity> users = userJPA.findAll();
        for (UserEntity user : users) {
            if (user.getUserName().contains(name))
                names.add(user.getUserName());
        }
        if (names.isEmpty()) names.add("没有查询到该数据");
        return names;
    }

    //获取好友信息
    public List<UserEntity> getFriends(int user_id) {
        return userJPA.findFriends(user_id);
    }

    //提交用户登录的设备号
    public Boolean commitDevice(int id, String device) {
        UserEntity user = userJPA.findOne(id);
        user.setDevice(device);
        userJPA.save(user);
        return true;
    }

    //获取对方的设备id
    public String getDevice(String name, int id) {
        UserEntity user = userJPA.findByUserName(name);
        List<FriendEntity> list = friendJPA.findAllByFriends_UserId2(user.getUserId());
        for (FriendEntity item : list) {
            if (item.getFriends().getUserId1() == id)
                return "-1";
        }
        String device = "\"" + user.getDevice() + "\"";
        return device;
    }

    //添加好友
    public Boolean addFriend(int id1, int id2) {
        FriendEntity.Friends friends = new FriendEntity.Friends();
        friends.setUserId1(id1);
        friends.setUserId2(id2);
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFriends(friends);
        //如果已经是好友了，则返回false
        List<FriendEntity> list = friendJPA.findAllByFriends_UserId1(id1);
        for (FriendEntity item : list) {
            if (item.getFriends().getUserId2() == friendEntity.getFriends().getUserId2())
                return false;
        }
        friendJPA.save(friendEntity);
        friends.setUserId1(id2);
        friends.setUserId2(id1);
        friendEntity.setFriends(friends);
        friendJPA.save(friendEntity);
        return true;
    }

    //上传头像
    public String updatePhoto(int user_id, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
                    new File("/usr/share/nginx/html/stepruler/" + String.valueOf(user_id) + filename.substring(filename.lastIndexOf(".")))
            ));
            buffStream.write(bytes);
            buffStream.close();
            UserEntity user = userJPA.findOne(user_id);

            String url = "http://39.108.173.192/stepruler/" + String.valueOf(user_id) + filename.substring(filename.lastIndexOf("."));
            user.setUserImg(url);
            userJPA.save(user);
            return "\"" + url + "\"";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "500";
    }

    //根据用户id返回用户名
    public List<String> getUserInfo(int id) {
        List<String> info = new ArrayList<>();
        info.add(userJPA.findByUserId(id).getUserName());
        info.add(userJPA.findByUserId(id).getUserImg());
        return info;
    }
}
