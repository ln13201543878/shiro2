package com.woniu.mapper;

import com.woniu.model.Role;
import com.woniu.model.RoleExample;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
	List findByUid(Integer uid);
	
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}