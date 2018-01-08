package com.example.stepruler.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friend")
public class FriendEntity {
    @EmbeddedId
    private Friends friends;

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    @Embeddable
    public static class Friends implements Serializable{
        @Column(name = "user_id1")
        private int userId1;
        @Column(name = "user_id2")
        private int userId2;

        public int getUserId1() {
            return userId1;
        }

        public int getUserId2() {
            return userId2;
        }

        public void setUserId1(int userId1) {
            this.userId1 = userId1;
        }

        public void setUserId2(int userId2) {
            this.userId2 = userId2;
        }
    }
}
