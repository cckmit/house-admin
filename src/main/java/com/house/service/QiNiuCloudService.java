package com.house.service;

import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.house.common.properties.QiniuProperties;
import com.house.exception.CheckException;
import com.house.model.Images;
import com.house.model.QiniuModel;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@Service
public class QiNiuCloudService {


    @Resource
    private QiniuProperties qiniuProperties;

    public Images uploadToQiNiuV2(MultipartFile multipartFile) throws IOException {
        QiniuModel qiniuModel = this.uploadToQiNiu(multipartFile);
        Images images = new Images();
        images.setUrl(qiniuModel.getUrl());
        images.setThumbUrl(qiniuModel.getUrl());
        images.setName(qiniuModel.getName());
        return  images;
    }
    public QiniuModel uploadToQiNiu(MultipartFile multipartFile) throws IOException {

        QiniuModel qiniuModel = new QiniuModel();
        //构造一个带指定 Region 对象的配置
        /*
         * 华东   Region.region0(), Region.huadong()
         * 华北   Region.region1(), Region.huabei()
         * 华南   Region.region2(), Region.huanan()
         * 北美   Region.regionNa0(), Region.beimei()
         * 东南亚	Region.regionAs0(), Region.xinjiapo()
         */
        Configuration cfg = new Configuration(Region.huadong());
        //文件上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucket());

        if (ObjectUtil.isNotEmpty(multipartFile)) {
            //给图片重新设定名字
            String fileName = multipartFile.getOriginalFilename();
            if (Objects.isNull(fileName)) {
                throw new CheckException("文件名不存在");
            }

//            String suffix = fileName.substring(fileName.lastIndexOf("."));
//            String uuid = IdUtil.randomUUID().toLowerCase().replace("-", "");
//            fileName = fileName;
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            Response response = uploadManager.put(multipartFile.getBytes(), null, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //成功 返回url name
            qiniuModel.setUrl("http://" + qiniuProperties.getDomain() + "/" + putRet.key);
            qiniuModel.setName(putRet.key);
            return qiniuModel;
        } else {
            throw new CheckException("未获取到上传的文件！");
        }

    }
}
