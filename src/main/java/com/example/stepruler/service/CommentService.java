package com.example.stepruler.service;

import com.example.stepruler.Entity.CommentEntity;
import com.example.stepruler.jpa.CommentJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentJPA commentJPA;

    //获取评论
    public ArrayList<CommentEntity> getComments(int id) {
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
    public ArrayList<CommentEntity> addComment(int id,
                                               String text,
                                               String username) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommunityId(id);
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
