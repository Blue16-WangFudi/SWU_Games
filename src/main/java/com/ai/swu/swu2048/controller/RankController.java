package com.ai.swu.swu2048.controller;

import com.ai.swu.swu2048.entity.PlayerRecord;
import com.ai.swu.swu2048.entity.TotalRecord;
import com.ai.swu.swu2048.mapper.PlayerRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RequestMapping("/SWU_Games/rank")
@RestController
public class RankController {

    @Autowired
    private PlayerRecordMapper playerRecordMapper;

    @PostMapping("/user_rank")
    public ResponseEntity<?> getUserRank(
            @RequestBody Map<String, Object> params) {
        String gameType = (String) params.get("gameType");
        String college = (String) params.get("college");
        String className = (String) params.get("className");
        String rankType = (String) params.get("rankType");
        String userID = (String) params.get("userID");

        int rank = playerRecordMapper.getUserRank(gameType, college, className, rankType, userID);
        if (rank == -1) {
            Map<String, String> response = new HashMap<>();
            response.put("msg", "User not found!");
            return ResponseEntity.badRequest().body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("rank", rank);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/top_n_records")
    public ResponseEntity<?> getTopNRecords(
            @RequestBody Map<String, Object> params) {
        int top_n = (int) params.get("top_n");
        String gameType = (String) params.get("gameType");
        String college = (String) params.get("college");
        String className = (String) params.get("className");
        String name = (String) params.get("name");
        // 初始化结果列表
        List<PlayerRecord> playerRecords;

        // 如果有名字参数，则仅以名字为查询参数，无需提供college和class参数
        if(name == null) {
            // 如果没有college和class参数，直接获取所有记录
            if (college == null && className == null) {
                playerRecords = playerRecordMapper.getPlayerRecordsByGameType(gameType);
            } else {
                // 如果有college参数，获取对应记录
                if (college != null) {
                    playerRecords = playerRecordMapper.getPlayerRecordsByCollegeAndGameType(college, gameType);
                } else {
                    // 如果有class参数，获取对应记录
                    playerRecords = playerRecordMapper.getPlayerRecordsByClassAndGameType(className, gameType);
                }
            }
        }else{
            playerRecords = playerRecordMapper.getPlayerRecordsByName(name);
        }

        // 根据mark排序
        playerRecords.sort(Comparator.comparingInt(PlayerRecord::getMark).reversed());

        // 如果top_n小于列表大小，且不等于0（等于0则是要所有结果）截取前top_n个记录
        if (top_n != 0 && top_n < playerRecords.size()) {
            playerRecords = playerRecords.subList(0, top_n);
        }

        if(playerRecords.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("msg", "Result Empty!");
            return ResponseEntity.badRequest().body(response);
        }else{
            return ResponseEntity.ok(playerRecords);
        }
    }

    @PostMapping("/top_n_players")
    public ResponseEntity<?> getTopNWithUser(
            @RequestBody Map<String, Object> params) {
        String gameType = (String) params.get("gameType");
        String college = (String) params.get("college");
        String className = (String) params.get("className");
        String rankType = (String) params.get("rankType");
        int top_n = (int) params.get("top_n");

        List<TotalRecord> topNRecords = playerRecordMapper.getTopNRanks(gameType, college, className, rankType, top_n);

        for (TotalRecord record : topNRecords) {
            record.setUserID(null); // 删除每条记录的userID
        }

        return ResponseEntity.ok(topNRecords);
    }
}
