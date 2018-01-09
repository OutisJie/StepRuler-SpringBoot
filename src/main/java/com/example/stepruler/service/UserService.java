package com.example.stepruler.service;

import com.example.stepruler.Entity.UserEntity;
import com.example.stepruler.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UserService {
    @Autowired
    UserJPA userJPA;

    public String updatePhoto(int user_id, MultipartFile file){
        try{
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
                    new File("/usr/share/nginx/html/stepruler/" + String.valueOf(user_id) + filename.substring(filename.lastIndexOf(".")))
            ));
            buffStream.write(bytes);
            buffStream.close();
            UserEntity user = userJPA.findOne(user_id);
            user.setUserImg(filename);
            userJPA.save(user);
            return "\"" + filename + "\"";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "500";
    }
}
