package com.example.stepruler.jpa;

import com.example.stepruler.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface CommentJPA extends
        JpaRepository<CommentEntity,Long>,
        JpaSpecificationExecutor<CommentEntity>,
        Serializable{
}
