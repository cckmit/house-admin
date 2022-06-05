package com.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.Room;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 根据房子id查询该房子下的所有房间
     */
    @Select("select * from room where room_house_id = #{houseid} order by room_number ")
    @Results({
            @Result(column = "room_tenant_id", property = "tenant", one = @One(select = "com.linhd.dao.ITenantDao.findTenantById", fetchType = FetchType.EAGER))
    })
    List<Room> findRoomByHouseId(String houseId);

    /**
     * 新增房间
     *
     * @param room
     * @return
     */
    @Insert("insert into room (id,room_house_id,room_number,room_price) values(#{id},#{room_house_id},#{room_number},#{room_price})")
    int addRoom(Room room);

    /**
     * 更新房间
     *
     * @param room
     * @return
     */
    @Update("update room set room_number = #{room_number} , room_tenant_id = #{room_tenant_id}, room_price = #{room_price} ,rental_date=#{rental_date}," +
            "rent_collection_date = #{rent_collection_date} where id = #{id}")
    int updRoom(Room room);

    /**
     * 根据用户id和房间id查询该用户是否已经租房
     *
     * @param tenant_id
     * @param room_id
     * @return
     */
    @Select("select count(1) from room where room_tenant_id = #{tenant_id} and id != #{room_id}")
    int findIsExistRoomTenant(String tenant_id, String room_id);

    /**
     * 根据id删除房间
     *
     * @param id
     * @return
     */
    @Delete("delete from room where id = #{id}")
    int delRoom(String id);

    /**
     * 根据房子id删除房间
     *
     * @param house_id
     * @return
     */
    @Delete("delete from room where room_house_id = #{house_id}")
    int delRoomByHouse(String house_id);

}
