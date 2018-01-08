package com.example.stepruler.service;

import com.example.stepruler.Entity.CommunityEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService{

    public List<CommunityEntity> cutPage(int page, int size, List<CommunityEntity> list){

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

    //查找
}
