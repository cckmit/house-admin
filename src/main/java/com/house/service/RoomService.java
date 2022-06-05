package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.Room;

public interface RoomService extends IService<Room> {

    IPage<Room> list(Room room);

}
