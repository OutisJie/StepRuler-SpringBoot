package com.example.stepruler.controller;

import com.example.stepruler.Entity.FriendEntity;
import com.example.stepruler.Entity.UserEntity;
import com.example.stepruler.builder.MD5Util;
import com.example.stepruler.jpa.FriendJPA;
import com.example.stepruler.jpa.UserJPA;
import com.example.stepruler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserJPA userJPA;

    @Autowired
    private FriendJPA friendJPA;

    @Autowired
    private UserService userService;

    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody UserEntity entity) {
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int login(@RequestParam("user_name") String name, @RequestParam("user_pwd") String pwd) {
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


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ArrayList<String> search(@RequestParam("user_name") String name) {
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

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<UserEntity> getFriends(@RequestParam("user_id") int user_id) {
        return userJPA.findFriends(user_id);
    }

    //提交用户登录的设备号
    @RequestMapping(value = "/postDevice", method = RequestMethod.POST)
    public Boolean commitDevice(@RequestParam("user_id") int id, @RequestParam("device_id") String device) {
        UserEntity user = userJPA.findOne(id);
        user.setDevice(device);
        userJPA.save(user);
        return true;
    }

    //获取对方的设备id
    @RequestMapping(value = "/getDevice", method = RequestMethod.GET)
    public String getDevice(@RequestParam("user_name") String name, @RequestParam("user_id") int id) {
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
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public Boolean addFriend(@RequestParam("user_id1") int id1, @RequestParam("user_id2") int id2) {
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

    //修改用户头像
    @RequestMapping(value = "/postImg", method = RequestMethod.POST)
    public String postImg(@PathParam("user_id") int user_id, @RequestParam("photo") MultipartFile file) {
        return userService.updatePhoto(user_id, file);
    }

    //根据用户id返回用户名
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public List<String> getUserInfo(@RequestParam("user_id") int id){
        List<String> info = new ArrayList<>();
        info.add(userJPA.findByUserId(id).getUserName());
        info.add(userJPA.findByUserId(id).getUserPwd());
        return info;
    }
}
