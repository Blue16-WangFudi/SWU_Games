package com.ai.swu.swu2048;

import com.ai.swu.swu2048.entity.SWUser;
import com.ai.swu.swu2048.mapper.SWUserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private SWUserMapper userMapper;
    @Autowired
    private LoginService loginService;

    public String authenticate(SWUser user) {
        SWUser existingUser = userMapper.findBySIDNumber(user.getSidNumber());
        if (existingUser != null && Objects.equals(existingUser.getPassword(), user.getPassword())) {
            // 找到了对应记录，直接获取
            return existingUser.getId();
        } else {
            // 创建一个新用户
            if(loginService.login(user.getSidNumber(),user.getPassword())){
                String[] infos=loginService.getInfo();
                user.setCollege(infos[0]);
                user.setClassName(infos[1]);
                user.setName(infos[2]);
                user.setType(infos[3]);
                String newId = UUID.randomUUID().toString();
                loginService.getPic(newId);
                loginService.closeConnection();
                user.setId(newId);
                user.setFirstLoginTime(new Date());
                userMapper.insertUser(user);
                return newId;
            }
            return null;
        }
    }

    public SWUser getUserById(String id) {
        return userMapper.findById(id);
    }
}
