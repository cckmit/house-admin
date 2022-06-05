package com.house.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendLog {
    private String userId;

    private String sendStatus;

    private Long sendTime;

    private String remark;

    private String type;

}
