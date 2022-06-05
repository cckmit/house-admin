package com.house.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备
 */
@Data
@NoArgsConstructor
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备表主键编号
     */
    @TableId(value = "device_id", type = IdType.AUTO)
    private Long deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 蓝牙名称
     */
    private String deviceBtName;

    /**
     * 安装时间
     */
    private Date installationTime;

    /**
     * 1.进闸口 2.车厢  3.出闸口
     */
    private Integer deviceType;

    /**
     * 设备安装地址
     */
    private String deviceAddrese;

    /**
     * 设备线路
     */
    private Integer deviceLine;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 设备编号
     */
    private String editDeviceId;

    /**
     * 小程序码
     */
    private String recodePath;
}