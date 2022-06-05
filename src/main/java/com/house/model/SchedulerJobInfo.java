package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "scheduler_job_info")
public class SchedulerJobInfo extends BaseBean{

    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;

    private String jobName;

    private String jobGroup;

    private String jobStatus;

    private String jobClass;

    @Schema(name = "cron 表达式")
    private String cronExpression;

    @TableField(value = "`desc`")
    private String desc;

    private String interfaceName;

    private Long repeatTime;

    private Boolean cronJob;
}