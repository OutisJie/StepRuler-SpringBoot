package com.example.stepruler.controller;

import com.example.stepruler.Entity.CommunityEntity;
import com.example.stepruler.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/community")
public class CommunityController {

    @Autowired
    private CommunityService service;

    @RequestMapping(value = "/iszan", method = RequestMethod.PUT)
    public int zan(@RequestParam("community_id") int id) {
        return service.zan(id);
    }

    @RequestMapping(value = "/notzan", method = RequestMethod.PUT)
    public int notzan(@RequestParam("community_id") int id) {
        return service.notzan(id);
    }

    //发说说
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String post(@RequestParam("user_id") int user_id,
                       @RequestParam("community_date") String date,
                       @RequestParam("community_text") String text,
                       @RequestParam("community_zan") int zan,
                       @RequestParam("community_comment") int comment) {
        return service.post(user_id, date, text, zan, comment);
    }


    //查询自己发过的朋友圈，按日期排序
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public List<CommunityEntity> mine(@RequestParam("user_id") int user_id, @RequestParam("page") int page) {
        return service.mine(user_id, page);
    }

    //删除自己发过的朋友圈操作
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public List<CommunityEntity> delete(@RequestParam("community_id") int communityId, @RequestParam("user_id") int id) {
        return service.delete(communityId, id);
    }

    //返回热门朋友圈，按评论数排序
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public List<CommunityEntity> getHot(@RequestParam("page") int page) {
        return service.getHot(page);
    }

    //返回我所关注的好友的朋友圈，按时间排序
    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public List<CommunityEntity> getFoll(@RequestParam("user_id") int user_id, @RequestParam("page") int page) {
        return service.getFoll(user_id, page);
    }
}