package com.woniu.mapper;

import com.woniu.model.Userinfo;
import com.woniu.model.UserinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserinfoMapper {
	Userinfo login(String uname,String upass);
	
    long countByExample(UserinfoExample example);

    int deleteByExample(UserinfoExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    List<Userinfo> selectByExample(UserinfoExample example);

    Userinfo selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Userinfo record, @Param("example") UserinfoExample example);

    int updateByExample(@Param("record") Userinfo record, @Param("example") UserinfoExample example);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
}