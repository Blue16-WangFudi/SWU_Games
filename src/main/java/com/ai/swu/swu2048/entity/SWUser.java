package com.ai.swu.swu2048.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SWUser {
    private String id;
    @NotNull(message = "学号不能为空")
    private String sidNumber;
    @NotNull(message = "密码不能为空")
    private String password;
    private String name;
    private String type;
    private String college;
    private String className;
    private java.util.Date firstLoginTime; // 使用java.util.Date类型来匹配datetime类型
}
