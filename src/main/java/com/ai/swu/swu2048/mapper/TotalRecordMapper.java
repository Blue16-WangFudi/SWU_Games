package com.ai.swu.swu2048.mapper;

import com.ai.swu.swu2048.entity.TotalRecord;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TotalRecordMapper {
    @Insert("INSERT INTO totalrecord (ID, UserID, GameType, Name, SIDNumber, College, ClassName, TotalMark, GameType) " +
            "VALUES (#{id}, #{userID}, #{gameType}, #{name}, #{sidNumber}, #{college}, #{className}, #{totalMark}, #{gameType})")
    void insertTotalRecord(TotalRecord totalRecord);

    @Select("SELECT TotalMark FROM totalrecord WHERE UserID = #{userID} AND GameType= #{gameType}")
    Integer selectTotalMarkByGameTypeAndUserID(String userID,String gameType);

    @Select("SELECT TopMark FROM totalrecord WHERE UserID = #{userID} AND GameType= #{gameType}")
    Integer selectTopMarkByGameTypeAndUserID(String userID,String gameType);

    @Select("SELECT * FROM totalrecord WHERE UserID = #{userID} AND GameType= #{gameType}")

    TotalRecord selectTotalRecordByGameTypeAndUserID(String userID,String gameType);
    @Update("UPDATE totalrecord SET TotalMark = #{totalMark} AND TopMark = #{topMark} WHERE UserID = #{userID}")
    void updateTotalMarkByGameTypeAndUserID(TotalRecord totalRecord);
}


