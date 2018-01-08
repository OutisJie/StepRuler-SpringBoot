package com.example.stepruler.Entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name="community")
public class CommunityEntity implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private int communityId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "community_date")
    private String communityDate;

    @Column(name = "community_text")
    private String communityText;

    @Column(name = "community_zan")
    private int communityZan;

    @Column(name = "community_comment")
    private int communityComment;

    public int getCommunityId() {
        return communityId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommunityDate() {
        return communityDate;
    }

    public int getCommunityZan() {
        return communityZan;
    }

    public String getCommunityText() {
        return communityText;
    }

    public int getCommunityComment() {
        return communityComment;
    }

    public void setCommunityZan(int communityZan) {
        this.communityZan = communityZan;
    }

    public void setCommunityComment(int communityComment) {
        this.communityComment = communityComment;
    }

    public void setCommunityDate(String communityDate) {
        this.communityDate = communityDate;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public void setCommunityText(String communityText) {
        this.communityText = communityText;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
