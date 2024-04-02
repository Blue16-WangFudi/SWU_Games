package com.ai.swu.swu2048;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 让MyBates扫描这里面的文件
@MapperScan("com.ai.swu.swu2048.mapper")
public class Swu2048Application {

    public static void main(String[] args) {
        SpringApplication.run(Swu2048Application.class, args);
    }

}
