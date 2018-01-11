package com.example.stepruler.controller;

import com.example.stepruler.Entity.UserEntity;
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
    private UserService userService;

    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody UserEntity entity) {
        return userService.register(entity);
    }


    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int login(@RequestParam("user_name") String name, @RequestParam("user_pwd") String pwd) {
        return userService.login(name, pwd);
    }

    //搜索
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ArrayList<String> search(@RequestParam("user_name") String name) {
        return userService.search(name);
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<UserEntity> getFriends(@RequestParam("user_id") int user_id) {
        return userService.getFriends(user_id);
    }

    //提交用户登录的设备号
    @RequestMapping(value = "/postDevice", method = RequestMethod.POST)
    public Boolean commitDevice(@RequestParam("user_id") int id, @RequestParam("device_id") String device) {
        return userService.commitDevice(id, device);
    }

    //获取对方的设备id
    @RequestMapping(value = "/getDevice", method = RequestMethod.GET)
    public String getDevice(@RequestParam("user_name") String name, @RequestParam("user_id") int id) {
        return userService.getDevice(name, id);
    }

    //添加好友
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public Boolean addFriend(@RequestParam("user_id1") int id1, @RequestParam("user_id2") int id2) {
        return userService.addFriend(id1, id2);
    }

    //修改用户头像
    @RequestMapping(value = "/postImg", method = RequestMethod.POST)
    public String postImg(@PathParam("user_id") int user_id, @RequestParam("photo") MultipartFile file) {
        return userService.updatePhoto(user_id, file);
    }

    //根据用户id返回用户名
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public List<String> getUserInfo(@RequestParam("user_id") int id) {
        return userService.getUserInfo(id);
    }
}