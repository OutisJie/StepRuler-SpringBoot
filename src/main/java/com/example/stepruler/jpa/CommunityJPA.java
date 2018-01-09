package com.example.stepruler.jpa;



import com.example.stepruler.Entity.CommunityEntity;
import com.example.stepruler.base.BaseRepository;


import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CommunityJPA extends BaseRepository<CommunityEntity, Integer>{
    //根据用户id查询他所发过的所有朋友圈
    @Query(value = "select * from community where user_id = ?1 ORDER BY community_date DESC ", nativeQuery = true)
    public List<CommunityEntity> findAllByUserId(int id);

    //根据用户的id查找他所关注的人以及他们发过的说说
    @Query(value = "select * from community where user_id in " +
            "(select user_id2 from friend where user_id1 = ?1) " +
            "ORDER BY community_comment DESC ",
            nativeQuery = true)
    public List<CommunityEntity> findByFriendId(int id);
}
