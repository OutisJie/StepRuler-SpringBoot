package com.example.stepruler.controller;

import com.example.stepruler.Entity.ArticleEntity;
import com.example.stepruler.Entity.TitleEntity;
import com.example.stepruler.jpa.ArticleJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {
    @Autowired
    private ArticleJPA articleJPA;

    @RequestMapping(path = "/list/{random}", method = RequestMethod.GET)
    public List<ArticleEntity> list(@PathVariable("random") int random){
        List<ArticleEntity> list = new ArrayList<>();
        List<ArticleEntity> list1 = articleJPA.findAll();

        for(int i = 0; i< random; i ++){
            int index = new Random().nextInt(list1.size());
            list.add(list1.get(index));
        }
        return list;
    }

    @RequestMapping(path = "/single/{id}", method = RequestMethod.GET)
    private ArticleEntity single(@PathVariable("id") long id){
        return  articleJPA.findOne(id);
    }

    @RequestMapping(path = "/search",method = RequestMethod.POST)
    private ArrayList<ArticleEntity> search(@RequestParam("searchData") String index){
        ArrayList<ArticleEntity>found=new ArrayList<>();
        if(index.equals("")){
            ArticleEntity articleEntity=new ArticleEntity();
            articleEntity.setArticleTitle("没有查询到该数据");
            found.add(articleEntity);
            return found;
        }
        List<ArticleEntity>list=articleJPA.findAll();
        for(ArticleEntity articleEntity:list) {
            String m_title=articleEntity.getArticleTitle();
            String m_subtitle=articleEntity.getArticleSubtitle();
            if(m_subtitle==null||m_subtitle.equals("")){
                if(m_title.contains(index)){
                    found.add(articleEntity);
                }
            }else{
                if (articleEntity.getArticleTitle().contains(index) || articleEntity.getArticleSubtitle().contains(index)){
                    found.add(articleEntity);
                }
            }
        }
        if(found.isEmpty()){
            ArticleEntity articleEntity=new ArticleEntity();
            articleEntity.setArticleTitle("没有查询到该数据");
            found.add(articleEntity);
        }
        return found;
    }

}
