package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.exception.CheckException;
import com.house.mapper.ImagesMapper;
import com.house.model.Images;
import com.house.service.ImagesService;
import com.house.utils.UserContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图片上传七牛云
 */
@Service
@Transactional
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images> implements ImagesService {

    @Override
    @Transactional
    public Boolean addImages(List<String> url, String foreignId, String imageType) {

        if (ObjectUtil.isNotEmpty(url)) {
            url.forEach(u -> {
                Images images = new Images();
                images.setOssUrl(u);
                images.setForeignId(foreignId);
                images.setImagesType(imageType);
                String imageName = u.substring(u.lastIndexOf("/") + 1);
                images.setImageName(imageName);
                this.save(images);
            });
        }
        return true;
    }

    @Override
    public List<Images> getImgList(Images images) {
        if (StringUtils.isBlank(images.getForeignId())) {
            throw new CheckException("关联ID参数不存在！");
        }
        if (StringUtils.isBlank(images.getImagesType())) {
            throw new CheckException("图片类型不存在！");
        }

        LambdaQueryWrapper<Images> imagesLqw = Wrappers.<Images>lambdaQuery()
                .eq(Images::getForeignId, images.getForeignId())
                .eq(Images::getImagesType, images.getImagesType())
                .eq(Images::getUserId, UserContextHolder.getUserId());
        List<Images> imagesList = this.list(imagesLqw);
        if (ObjectUtil.isNotEmpty(imagesList)) {
            imagesList.forEach(im -> {
                im.setUid(im.getImagesId() + "");
                im.setUrl(im.getOssUrl());
                im.setName(im.getImageName());
            });
        }
        return imagesList;
    }
}