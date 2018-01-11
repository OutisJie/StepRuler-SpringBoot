package com.example.stepruler.service;

import com.example.stepruler.Entity.ArticleEntity;
import com.example.stepruler.jpa.ArticleJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ArticleService {
    @Autowired
    private ArticleJPA articleJPA;

    public List<ArticleEntity> list(int random) {
        List<ArticleEntity> list = new ArrayList<>();
        List<ArticleEntity> list1 = articleJPA.findAll();

        for (int i = 0; i < random; i++) {
            int index = new Random().nextInt(list1.size());
            list.add(list1.get(index));
        }
        return list;
    }

    public ArticleEntity single(long id) {
        return articleJPA.findOne(id);
    }

    public ArrayList<ArticleEntity> search(String index) {
        ArrayList<ArticleEntity> found = new ArrayList<>();
        if (index.equals("")) {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setArticleTitle("没有查询到该数据");
            found.add(articleEntity);
            return found;
        }
        List<ArticleEntity> list = articleJPA.findAll();
        for (ArticleEntity articleEntity : list) {
            String m_title = articleEntity.getArticleTitle();
            String m_subtitle = articleEntity.getArticleSubtitle();
            if (m_subtitle == null || m_subtitle.equals("")) {
                if (m_title.contains(index)) {
                    found.add(articleEntity);
                }
            } else {
                if (articleEntity.getArticleTitle().contains(index) || articleEntity.getArticleSubtitle().contains(index)) {
                    found.add(articleEntity);
                }
            }
        }
        if (found.isEmpty()) {
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setArticleTitle("没有查询到该数据");
            found.add(articleEntity);
        }
        return found;
    }

}
