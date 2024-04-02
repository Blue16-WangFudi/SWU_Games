package com.ai.swu.swu2048.mapper;

import com.ai.swu.swu2048.entity.PlayerRecord;
import com.ai.swu.swu2048.entity.TotalRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PlayerRecordMapper {
    @Select("SELECT * FROM playerrecord WHERE GameType = #{gameType} ORDER BY Mark ASC")
    List<PlayerRecord> getPlayerRecordsByGameType(@Param("gameType") String gameType);
    @Select("SELECT * FROM playerrecord WHERE Name = #{name} ORDER BY Mark ASC")
    List<PlayerRecord> getPlayerRecordsByName(@Param("name") String name);
    @Select("SELECT * FROM playerrecord WHERE College = #{college} AND GameType = #{gameType} ORDER BY Mark ASC")
    List<PlayerRecord> getPlayerRecordsByCollegeAndGameType(@Param("college") String college, @Param("gameType") String gameType);

    @Select("SELECT * FROM playerrecord WHERE ClassName = #{className} AND GameType = #{gameType} ORDER BY Mark ASC")
    List<PlayerRecord> getPlayerRecordsByClassAndGameType(@Param("className") String className, @Param("gameType") String gameType);

    @Insert("INSERT INTO playerrecord (ID, GameType, Name, SIDNumber, College, ClassName, StartTime, EndTime, Mark) " +
            "VALUES (#{id}, #{gameType}, #{name}, #{sidNumber}, #{college}, #{className}, #{startTime}, #{endTime}, #{mark})")
    void insertPlayerRecord(PlayerRecord playerRecord);

    @Mapper
    int getUserRank(String gameType, String college, String className, String rankType, String userID);

    @Mapper
    List<TotalRecord> getTopNRanks(String gameType, String college, String className, String rankType, int top_n);
}


