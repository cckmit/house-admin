package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.model.Images;
import com.house.model.QiniuModel;
import com.house.service.QiNiuCloudService;
import com.house.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    private UploadService uploadService;
    private QiNiuCloudService qiNiuCloudService;

    @Autowired
    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Autowired
    public void setQiNiuCloudService(QiNiuCloudService qiNiuCloudService) {
        this.qiNiuCloudService = qiNiuCloudService;
    }

    @PostMapping("/upload")
    public ResultBean<QiniuModel> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResultBean.ok(qiNiuCloudService.uploadToQiNiu(multipartFile));
    }

    @PostMapping("/upload/v2")
    public ResultBean<Images> uploadV2(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResultBean.ok(qiNiuCloudService.uploadToQiNiuV2(multipartFile));
    }


}
