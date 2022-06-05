package com.house.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.mapper.UrlShMapper;
import com.house.model.UrlSh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UrlShService extends ServiceImpl<UrlShMapper, UrlSh> {

    private HandleProcessor handleProcessor;

    @Autowired
    public void setHandleProcessor(HandleProcessor handleProcessor) {
        this.handleProcessor = handleProcessor;
    }

    public Boolean loadData() throws InterruptedException {
        // 查询网址
        LambdaQueryWrapper<UrlSh> urlShLqw = Wrappers.<UrlSh>lambdaQuery()
                .eq(UrlSh::getSyncStatus, 0);
        List<UrlSh> urlShList = this.list(urlShLqw);
        for (UrlSh url : urlShList) {
            handleProcessor.spiderData(url.getUrl(), url.getCreateDate());
            Thread.sleep(2000);
            url.setSyncStatus(1);
        }

        return this.updateBatchById(urlShList);

    }
}
