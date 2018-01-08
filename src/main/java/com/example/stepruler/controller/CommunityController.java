package com.example.stepruler.controller;

import com.example.stepruler.Entity.CommunityEntity;
import com.example.stepruler.builder.SortBuilder;
import com.example.stepruler.jpa.CommunityJPA;
import com.example.stepruler.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/community")
public class CommunityController {
    @Autowired
    private CommunityJPA communityJPA;

    @Autowired
    private CommunityService service;

    @RequestMapping(value = "/iszan",method = RequestMethod.PUT)
    public int zan(@RequestParam("community_id") int id){
        List<CommunityEntity>list=communityJPA.findAll();
        for(CommunityEntity communityEntity:list){
            if(communityEntity.getCommunityId()==id) {
                communityEntity.setCommunityZan(communityEntity.getCommunityZan() + 1);
                communityJPA.save(communityEntity);
                return communityEntity.getCommunityZan();
            }
        }
        return -1;
    }
    @RequestMapping(value = "/notzan",method = RequestMethod.PUT)
    public int notzan(@RequestParam("community_id") int id){
        List<CommunityEntity>list=communityJPA.findAll();
        for(CommunityEntity communityEntity:list){
            if(communityEntity.getCommunityId()==id){
                communityEntity.setCommunityZan(communityEntity.getCommunityZan()-1);
                communityJPA.save(communityEntity);
                return communityEntity.getCommunityZan();
            }
        }
        return -1;
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String post(@RequestBody CommunityEntity communityEntity){
        communityJPA.save(communityEntity);
        return "success";
    }

    //查询自己发过的朋友圈，按日期排序
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public List<CommunityEntity> mine(@RequestParam("user_id") int user_id, @RequestParam("page") int page) {
        int size = 5;
        List<CommunityEntity> list = service.cutPage(page, size, communityJPA.findAllByUserId(user_id));
        return list;
    }

    //删除自己发过的朋友圈操作
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public boolean delete(@RequestParam("community_id") long community_id) {
        if (communityJPA.exists(community_id)) {
            communityJPA.delete(community_id);
            return true;
        } else {
            return false;
        }
    }

    //返回热门朋友圈，按评论数排序
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public List<CommunityEntity> getHot(@RequestParam("page") int page){
        int size = 5;
        List<CommunityEntity> list = communityJPA.findAll(SortBuilder.generateSort("communityComment/d"));
        return service.cutPage(page, size, list);
    }

    //返回我所关注的好友的朋友圈，按时间排序
    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public List<CommunityEntity> getFoll(@RequestParam("user_id") int user_id, @RequestParam("page") int page){
        int size = 5;
        return service.cutPage(page,size,communityJPA.findByFriendId(user_id));
    }
}