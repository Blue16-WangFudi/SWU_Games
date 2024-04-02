package com.ai.swu.swu2048.controller;

import com.ai.swu.swu2048.AuthService;
import com.ai.swu.swu2048.entity.SWUser;
import com.ai.swu.swu2048.entity.TotalRecord;
import com.ai.swu.swu2048.mapper.TotalRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/SWU_Games/")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    // 指定图片存储的相对路径（相对于classpath）
    @Autowired
    private TotalRecordMapper totalRecordMapper;
    private final Path imageStoragePath = Paths.get("src/main/resources/public/images").toAbsolutePath().normalize();

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody SWUser user) {
        String authResult = authService.authenticate(user);
        Map<String, String> response = new HashMap<>();
        if (authResult != null) {
            // 成功时，返回包含id的Map
            response.put("msg", "Login Successful!");
            response.put("id", authResult);
            return ResponseEntity.ok(response);
        } else {
            // 失败时，返回错误消息
            response.put("msg", "Login Failed!");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/data/user/info/{id}/pic")
    public ResponseEntity<?> getUserPic(@PathVariable String id) {
        try {
            // 构建图片的完整路径
            Path imagePath = imageStoragePath.resolve(id + ".png");
            Resource imageResource = new UrlResource(imagePath.toUri());

            if (imageResource.exists() || imageResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(imageResource);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("msg", "QueryPic Failed!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/data/user/info/{id}/detail")
    public ResponseEntity<?> getUserDetail(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        SWUser user = authService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            response.put("msg", "QueryDetail Failed!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/data/{UserID}/total/{GameType}")
    public ResponseEntity<?> getTotalRecord(@PathVariable String UserID,@PathVariable String GameType) {
        TotalRecord currentTotalMark = totalRecordMapper.selectTotalRecordByGameTypeAndUserID(UserID,GameType);
        if (currentTotalMark != null) {
            currentTotalMark.setUserID(null);// 防止id泄露
            // 记录存在，直接返回
            return ResponseEntity.ok(currentTotalMark);
        } else {
            // 记录不存在，返回0
            Map<String,String> response = new HashMap<>();
            response.put("msg","Query Failed.");
            return ResponseEntity.badRequest().body(response);
        }
    }
}

