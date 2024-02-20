package com.wanli.controller;

import com.wanli.result.R;
import com.wanli.utils.OSSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/oss")
public class OSSController {
    @Resource
    private OSSUtil ossUtil;
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file){
        String imgPath = ossUtil.uploadOneFile(file);
        if(imgPath == null){
            return R.error(400,"图片上传失败");
        }
        return R.ok().data("imagePath",imgPath);
    }
}