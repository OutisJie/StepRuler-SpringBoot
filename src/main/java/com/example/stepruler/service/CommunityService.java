package com.example.stepruler.service;

import com.example.stepruler.Entity.CommunityEntity;
import com.example.stepruler.builder.SortBuilder;
import com.example.stepruler.jpa.CommentJPA;
import com.example.stepruler.jpa.CommunityJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {

    @Autowired
    CommunityJPA communityJPA;

    public List<CommunityEntity> cutPage(int page, int size, List<CommunityEntity> list) {

        List<CommunityEntity> list1 = new ArrayList<>();

        if ((page - 1) * size >= list.size() && list.size() > size) {
            //没有那么多页，传回去一个空队列
            return list1;
        } else if (page * size >= list.size()) {
            //当前页不够十个,也就是说最后一页咯
            for (int i = 0; i < (list.size() - (page - 1) * size); i++) {
                list1.add(list.get((page - 1) * size + i));
            }
            return list1;
        } else if (page * size < list.size()) {
            //不是最后一页
            for (int i = 0; i < size; i++) {
                list1.add(list.get((page - 1) * size + i));
            }
            return list1;
        }
        return list1;
    }

    //点赞
    public int zan(int id) {
        List<CommunityEntity> list = communityJPA.findAll();
        for (CommunityEntity communityEntity : list) {
            if (communityEntity.getCommunityId() == id) {
                communityEntity.setCommunityZan(communityEntity.getCommunityZan() + 1);
                communityJPA.save(communityEntity);
                return communityEntity.getCommunityZan();
            }
        }
        return -1;
    }

    //取消点赞
    public int notzan(int id) {
        List<CommunityEntity> list = communityJPA.findAll();
        for (CommunityEntity communityEntity : list) {
            if (communityEntity.getCommunityId() == id) {
                communityEntity.setCommunityZan(communityEntity.getCommunityZan() - 1);
                communityJPA.save(communityEntity);
                return communityEntity.getCommunityZan();
            }
        }
        return -1;
    }

    //发说说
    public String post(int user_id,
                       String date,
                       String text,
                       int zan,
                       int comment) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setCommunityZan(zan);
        communityEntity.setCommunityComment(comment);
        communityEntity.setCommunityDate(date);
        communityEntity.setCommunityText(text);
        communityEntity.setUserId(user_id);
        communityJPA.save(communityEntity);
        return "success";
    }


    //查询自己发过的朋友圈，按日期排序
    public List<CommunityEntity> mine(int user_id, int page) {
        int size = 5;
        List<CommunityEntity> list = communityJPA.findAllByUserId(user_id);
        return cutPage(page, size, list);
    }

    //删除自己发过的朋友圈操作
    public List<CommunityEntity> delete(int communityId, int id) {
        communityJPA.delete(communityId);
        List<CommunityEntity> list = communityJPA.findAllByUserId(id);
        return list;
    }

    //返回热门朋友圈，按评论数排序
    public List<CommunityEntity> getHot(int page) {
        int size = 5;
        List<CommunityEntity> list = communityJPA.findAll(SortBuilder.generateSort("communityComment/d"));
        return cutPage(page, size, list);
    }

    //返回我所关注的好友的朋友圈，按时间排序
    public List<CommunityEntity> getFoll(int user_id, int page) {
        int size = 5;
        return cutPage(page, size, communityJPA.findByFriendId(user_id));
    }
}
