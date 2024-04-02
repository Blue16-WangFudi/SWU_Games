package com.ai.swu.swu2048.entity;

import lombok.Data;

@Data
public class TotalRecord {
    private String id;
    private String userID;
    private String gameType;
    private String name;
    private String sidNumber;
    private String college;
    private String className;
    private Integer totalMark;
    private Integer topMark;
}
