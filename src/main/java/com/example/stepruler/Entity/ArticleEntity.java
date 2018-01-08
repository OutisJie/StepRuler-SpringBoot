package com.example.stepruler.Entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "article")
public class ArticleEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private long articleId;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "article_subtitle")
    private String articleSubtitle;

    @Column(name = "article_author")
    private String articleAuthor;

    @Column(name = "article_date")
    private String articleDate;

    @Column(name = "article_theme")
    private String articleTheme;

    @Column(name = "article_bodyhtml")
    private String articleBodyhtml;

    @Column(name = "article_img")
    private String articleImg;

    @Column(name = "article_imgs")
    private String articleImgs;

    @Column(name = "article_text")
    private String ArticleText;

    public long getArticleId() {
        return articleId;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public String getArticleBodyhtml() {
        return articleBodyhtml;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public String getArticleImgs() {
        return articleImgs;
    }

    public String getArticleSubtitle() {
        return articleSubtitle;
    }

    public String getArticleTheme() {
        return articleTheme;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public void setArticleBodyhtml(String articleBodyhtml) {
        this.articleBodyhtml = articleBodyhtml;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public void setArticleImgs(String articleImgs) {
        this.articleImgs = articleImgs;
    }

    public void setArticleSubtitle(String articleSubtitle) {
        this.articleSubtitle = articleSubtitle;
    }

    public void setArticleTheme(String articleTheme) {
        this.articleTheme = articleTheme;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleText() {
        return ArticleText;
    }

    public void setArticleText(String articleText) {
        ArticleText = articleText;
    }
}
