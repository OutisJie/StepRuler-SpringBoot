package com.example.stepruler.jpa;

import com.example.stepruler.Entity.FriendEntity;
import com.example.stepruler.base.BaseRepository;

import java.util.List;

public interface FriendJPA extends BaseRepository<FriendEntity, Integer> {
    //根据id查找他所关注的人的id
    public List<FriendEntity> findAllByFriends_UserId1(int id1);

    //根据id查找他的粉丝
    public List<FriendEntity> findAllByFriends_UserId2(int id2);
}
