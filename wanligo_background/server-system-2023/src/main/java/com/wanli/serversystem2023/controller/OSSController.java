package com.wanli.serversystem2023.controller;

import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
public class OSSController {
    @Autowired
    OSSUtil ossUtil;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file){
        String imgPath = ossUtil.uploadOneFile(file);
        if(imgPath == null){
            return Result.fail("图片上传失败");
        }
        return Result.success(imgPath);
    }

}
