package com.example.stepruler.jpa;

import com.example.stepruler.Entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface ArticleJPA extends
        JpaRepository<ArticleEntity,Long>,
        JpaSpecificationExecutor<ArticleEntity>,
        Serializable {
}
