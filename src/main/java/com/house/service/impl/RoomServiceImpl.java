package com.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.mapper.RoomMapper;
import com.house.model.Room;
import com.house.model.UserInfo;
import com.house.service.RoomService;
import com.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {


    @Autowired
    private UserService userService;

    @Override
    public IPage<Room> list(Room room) {
        UserInfo userInfo = userService.getCurrentUserInfo();

        LambdaQueryWrapper<Room> lqw = Wrappers.<Room>lambdaQuery()
                .eq(Room::getUserId, userInfo.getUserId())
                .eq(Objects.nonNull(room.getRoomNumber()), Room::getRoomNumber, room.getRoomNumber());

        Page<Room> roomsPage = new Page<>(room.getPageNo(), room.getPageSize());

        return baseMapper.selectPage(roomsPage, lqw);
    }
}
