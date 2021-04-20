package com.zzb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif", "image/png");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    public String uploadImg(MultipartFile file){

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        //校验文件类型
        if(!CONTENT_TYPES.contains(contentType)){
            return null;
        }

        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read==null){
                return null;
            }

            file.transferTo(new File("D:\\IdeaProjects\\images\\" + originalFilename));

            return "http://image.zzb.com/" + originalFilename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
