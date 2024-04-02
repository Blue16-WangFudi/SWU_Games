package com.ai.swu.swu2048.entity;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerRecord {
    private String id;
    @NotNull(message = "游戏类型不能为空！")
    private String gameType;
    private String name;
    private String sidNumber;
    private String college;
    private String className;
    private Date startTime;
    private Date endTime;
    @NotNull(message = "得分不能为空！")
    private Integer mark;
}

