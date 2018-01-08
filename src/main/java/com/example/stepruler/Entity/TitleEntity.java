package com.example.stepruler.Entity;


import org.springframework.beans.factory.annotation.Autowired;

public class TitleEntity {
    private String title;
    private String subtitle;
    public TitleEntity(){
        title=null;
        subtitle=null;
    }
    public TitleEntity(String s1,String s2){
        title=s1;
        subtitle=s2;
    }

    public void setTitle(String s){title=s;}
    public void setSubtitle(String s){subtitle=s;}

    public String getTitle(){return  title;}
    public String getSubtitle(){return subtitle;}
}
