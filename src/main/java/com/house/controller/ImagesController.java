package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.model.Images;
import com.house.service.ImagesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "资料上传")
@RestController
@RequestMapping("images")
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    @Operation(summary ="获取资料图片")
    @PostMapping(value = "list")
    public ResultBean<List<Images>> getImgList(@RequestBody Images images) {
        return ResultBean.ok(imagesService.getImgList(images));
    }

}
