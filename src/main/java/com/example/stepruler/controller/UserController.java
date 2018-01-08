package com.example.stepruler.controller;

import com.example.stepruler.Entity.FriendEntity;
import com.example.stepruler.Entity.UserEntity;
import com.example.stepruler.jpa.FriendJPA;
import com.example.stepruler.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserJPA userJPA;

    @Autowired
    private FriendJPA friendJPA;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody UserEntity entity){
        List<UserEntity> users=userJPA.findAll();
        for(UserEntity user:users){
            if(entity.getUserName().equals(user.getUserName())) {
                return "failed";
            }
        }
        entity.setDevice("-2");
        userJPA.save(entity);
        return "success";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public int login(@RequestBody UserEntity entity){
        List<UserEntity>users=userJPA.findAll();
        for(UserEntity user:users){
            if(entity.getUserName().equals(user.getUserName())&&entity.getUserPwd().equals(user.getUserPwd())){
                return user.getUserId();
            }
            if(entity.getUserName().equals(user.getUserName())&&!entity.getUserPwd().equals(user.getUserPwd())){
                return -2;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ArrayList<String> search(@RequestParam("user_name") String name){
        ArrayList<String> names=new ArrayList<>();
        if(name==""){
            names.add("没有查询到该数据");
            return names;
        }
        List<UserEntity>users=userJPA.findAll();
        for(UserEntity user:users){
            if(user.getUserName().contains(name))
                names.add(user.getUserName());
        }
        if(names.isEmpty())names.add("没有查询到该数据");
        return names;
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public List<UserEntity> getFriends(@RequestParam("user_id") int user_id){
        return userJPA.findFriends(user_id);
    }

    //提交用户登录的设备号
    @RequestMapping(value = "/postDevice", method = RequestMethod.POST)
    public Boolean commitDevice(@RequestParam("user_id") int id, @RequestParam("device_id") String device){
        UserEntity user = userJPA.findOne(id);
        user.setDevice(device);
        userJPA.save(user);
        return true;
    }

    //获取对方的设备id
    @RequestMapping(value = "/getDevice", method = RequestMethod.GET)
    public String getDevice(@RequestParam("user_name") String name, @RequestParam("user_id") int id){
        UserEntity user = userJPA.findByUserName(name);
        List<FriendEntity> list = friendJPA.findAllByFriends_UserId2(user.getUserId());
        for(FriendEntity item : list){
            if(item.getFriends().getUserId1() == id)
                return "-1";
        }
        return user.getDevice();
    }

    //添加好友
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    private Boolean addFriend(@RequestParam("user_id1") int id1, @RequestParam("user_id2") int id2){
        FriendEntity.Friends friends = new FriendEntity.Friends();
        friends.setUserId1(id1);
        friends.setUserId2(id2);
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFriends(friends);
        //如果已经是好友了，则返回false
        List<FriendEntity> list = friendJPA.findAllByFriends_UserId1(id1);
        for(FriendEntity item : list) {
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
}
