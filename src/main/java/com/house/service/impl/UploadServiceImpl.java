package com.house.service.impl;

import com.house.service.QiNiuCloudService;
import com.house.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    private QiNiuCloudService qiNiuCloudService;

    @Autowired
    public void setQiNiuCloudService(QiNiuCloudService qiNiuCloudService) {
        this.qiNiuCloudService = qiNiuCloudService;
    }


}
