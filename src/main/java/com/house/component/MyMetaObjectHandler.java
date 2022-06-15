package com.house.component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.house.utils.DateUtils;
import com.house.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增时开始填充字段...");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createName", String.class, UserContextHolder.getUserInfo().getUsername());
        this.strictInsertFill(metaObject, "updateName", String.class, UserContextHolder.getUserInfo().getUsername());
        this.strictInsertFill(metaObject, "userId", String.class, UserContextHolder.getUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新时开始填充字段....");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateName", String.class, UserContextHolder.getUserInfo().getUsername());
    }
}