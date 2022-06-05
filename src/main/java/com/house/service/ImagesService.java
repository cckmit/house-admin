package com.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.Images;

import java.util.List;

/**
 *
 */
public interface ImagesService extends IService<Images> {
    Boolean addImages(List<String> url, String foreignId, String imageType);

    List<Images> getImgList(Images images);
}
