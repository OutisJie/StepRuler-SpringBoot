package com.example.stepruler.controller;

import com.example.stepruler.Entity.ArticleEntity;
import com.example.stepruler.Entity.TitleEntity;
import com.example.stepruler.jpa.ArticleJPA;
import com.example.stepruler.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = "/list/{random}", method = RequestMethod.GET)
    public List<ArticleEntity> list(@PathVariable("random") int random) {
       return articleService.list(random);
    }

    @RequestMapping(path = "/single/{id}", method = RequestMethod.GET)
    public ArticleEntity single(@PathVariable("id") long id) {
        return articleService.single(id);
    }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public ArrayList<ArticleEntity> search(@RequestParam("searchData") String index) {
       return articleService.search(index);
    }

}
