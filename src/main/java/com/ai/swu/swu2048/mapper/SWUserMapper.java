package com.ai.swu.swu2048.mapper;

import com.ai.swu.swu2048.entity.SWUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface SWUserMapper {
    @Select("SELECT * FROM SWUser WHERE SIDNumber = #{SIDNumber}")
    SWUser findBySIDNumber(@Param("SIDNumber") String SIDNumber);

    @Insert("INSERT INTO SWUser (ID, SIDNumber, password, name, type, college, className, firstLoginTime) " +
            "VALUES (#{id}, #{sidNumber}, #{password}, #{name}, #{type}, #{college}, #{className}, #{firstLoginTime})")
    void insertUser(SWUser user);

    @Select("SELECT * FROM SWUser WHERE ID = #{ID}")
    SWUser findById(@Param("ID") String ID);
}

