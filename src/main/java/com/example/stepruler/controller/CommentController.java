package com.example.stepruler.controller;


import com.example.stepruler.Entity.CommentEntity;
import com.example.stepruler.jpa.CommentJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentJPA commentJPA;

    //获取评论
    @RequestMapping(value = "/getComments", method = RequestMethod.POST)
    public ArrayList<CommentEntity> getComments(@RequestParam("community_id") int id) {
        List<CommentEntity> commentEntities = commentJPA.findAll();
        ArrayList<CommentEntity> found = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            if (commentEntity.getCommunityId() == id) {
                found.add(commentEntity);
            }
        }
        return found;
    }

    //添加评论
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ArrayList<CommentEntity> addComment(@RequestParam("community_id") int id,
                                               @RequestParam("comment_text") String text,
                                               @RequestParam("user_name") String username) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(id);
        commentEntity.setCommentText(text);
        commentEntity.setUserName(username);
        commentJPA.save(commentEntity);
        List<CommentEntity> commentEntities = commentJPA.findAll();
        ArrayList<CommentEntity> found = new ArrayList<>();
        for (CommentEntity a : commentEntities) {
            if (a.getCommunityId() == id) {
                found.add(a);
            }
        }
        return found;
    }
}
