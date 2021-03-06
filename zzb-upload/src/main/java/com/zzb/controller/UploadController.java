package com.zzb.controller;

import com.zzb.common.api.Result;
import com.zzb.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public Result uploadImg(@RequestParam("file") MultipartFile file){
        String url = this.uploadService.uploadImg(file);
        if(StringUtils.isBlank(url)){
            return Result.failed("上传失败！");
        }
        return Result.success(url);
    }
}
