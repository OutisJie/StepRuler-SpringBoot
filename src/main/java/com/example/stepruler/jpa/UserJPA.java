package com.example.stepruler.jpa;

import com.example.stepruler.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface UserJPA extends
        JpaRepository<UserEntity,Integer>,
        JpaSpecificationExecutor<UserEntity>,
        Serializable{

    //根据用户id查找他的好友
    @Query(value = "select * from user where user_id in (select user_id2 from friend where user_id1 = ?1)", nativeQuery = true)
    public List<UserEntity> findFriends(int id);

    public UserEntity findByUserName(String naem);

}
