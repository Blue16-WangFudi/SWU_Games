package com.ai.swu.swu2048;

import com.ai.swu.swu2048.entity.PlayerRecord;
import com.ai.swu.swu2048.entity.SWUser;
import com.ai.swu.swu2048.mapper.PlayerRecordMapper;
import com.ai.swu.swu2048.mapper.SWUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerRecordService {

    @Autowired
    private PlayerRecordMapper playerRecordMapper;

    @Autowired
    private SWUserMapper swUserMapper;

    public void addPlayerRecord(PlayerRecord playerRecord) {

        // 根据sidNumber查询swuser表获取College和ClassName
        SWUser swUser = swUserMapper.findById(playerRecord.getId());

        // 生成UUID作为主键
        playerRecord.setId(UUID.randomUUID().toString());


        if (swUser != null) {
            playerRecord.setSidNumber(swUser.getSidNumber());
            playerRecord.setName(swUser.getName());
            playerRecord.setCollege(swUser.getCollege());
            playerRecord.setClassName(swUser.getClassName());
        }

        // 将playerRecord添加到playerrecord表
        playerRecordMapper.insertPlayerRecord(playerRecord);
    }
}

