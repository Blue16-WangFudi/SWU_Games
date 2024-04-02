package com.ai.swu.swu2048.controller;

import com.ai.swu.swu2048.PlayerRecordService;
import com.ai.swu.swu2048.entity.PlayerRecord;
import com.ai.swu.swu2048.entity.SWUser;
import com.ai.swu.swu2048.entity.TotalRecord;
import com.ai.swu.swu2048.mapper.SWUserMapper;
import com.ai.swu.swu2048.mapper.TotalRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class RecordController {

    @Autowired
    private PlayerRecordService playerRecordService;
    @Autowired
    private SWUserMapper userMapper;
    @Autowired
    private TotalRecordMapper totalRecordMapper;
    @Autowired
    private SWUserMapper swUserMapper;

    @PostMapping("/SWU_Games/record")
    public ResponseEntity<?> addPlayerRecord(@RequestBody Map<String, Object> params) {
        Map<String, String> response = new HashMap<>();
        // 获取提交的参数
        int mark = (int) params.get("mark");
        String id = (String) params.get("id");
        String gameType = (String) params.get("gameType");
        Date startTime = (Date) params.get("startTime");
        Date endTime = (Date) params.get("endTime");

        // 先通过id拿到用户基本信息
        SWUser swUser = userMapper.findById(id);
        if(swUser==null){
            response.put("msg", "Insert Failed!");
            return ResponseEntity.badRequest().body(response);
        }

        // 实例化一个完整的PlayerRecord
        PlayerRecord playerRecord = new PlayerRecord();

        // 将用户基本信息置入
        playerRecord.setName(swUser.getName());
        playerRecord.setCollege(swUser.getCollege());
        playerRecord.setClassName(swUser.getClassName());
        playerRecord.setSidNumber(swUser.getSidNumber());

        // 提交参数置入
        playerRecord.setGameType(gameType);
        playerRecord.setStartTime(startTime);
        playerRecord.setEndTime(endTime);
        playerRecord.setMark(mark);

        playerRecordService.addPlayerRecord(playerRecord);

        // 更新用户总分
        TotalRecord totalRecord = new TotalRecord();
        totalRecord.setId(UUID.randomUUID().toString());
        totalRecord.setName(swUser.getName());
        totalRecord.setSidNumber(swUser.getSidNumber());
        totalRecord.setCollege(swUser.getCollege());
        totalRecord.setClassName(swUser.getClassName());
        totalRecord.setUserID(id);
        totalRecord.setTotalMark(mark);
        totalRecord.setTopMark(mark);
        totalRecord.setGameType(gameType);
        addTotalRecord(totalRecord);
        response.put("msg", "Insert Successful!");
        return ResponseEntity.ok(response);
    }

    public void addTotalRecord(TotalRecord totalRecord) {
        String UserID=totalRecord.getUserID();
        String GameType=totalRecord.getGameType();
        Integer currentTotalMark = totalRecordMapper.selectTotalMarkByGameTypeAndUserID(UserID,GameType);
        Integer currentTopMark = totalRecordMapper.selectTopMarkByGameTypeAndUserID(UserID,GameType);
        if (currentTotalMark != null) {
            // 记录存在，更新TotalMark
            int newTotalMark = currentTotalMark + totalRecord.getTotalMark();
            int newTopMark = currentTopMark<totalRecord.getTopMark()?totalRecord.getTopMark():currentTopMark;
            totalRecord.setTotalMark(newTotalMark);
            totalRecord.setTopMark(newTopMark);
            totalRecordMapper.updateTotalMarkByGameTypeAndUserID(totalRecord);
        } else {
            // 记录不存在，插入新记录
            totalRecordMapper.insertTotalRecord(totalRecord);
        }
    }
}

