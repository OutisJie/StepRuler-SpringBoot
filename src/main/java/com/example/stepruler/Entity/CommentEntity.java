package com.example.stepruler.Entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment")
public class CommentEntity implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "community_id")
    private int communityId;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "user_name")
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
