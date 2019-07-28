package com.woniu.mapper;

import com.woniu.model.Tree;
import com.woniu.model.TreeExample;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface TreeMapper {
    List findTreeWithUid(Integer uid);
	
    long countByExample(TreeExample example);

    int deleteByExample(TreeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tree record);

    int insertSelective(Tree record);

    List<Tree> selectByExample(TreeExample example);

    Tree selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tree record, @Param("example") TreeExample example);

    int updateByExample(@Param("record") Tree record, @Param("example") TreeExample example);

    int updateByPrimaryKeySelective(Tree record);

    int updateByPrimaryKey(Tree record);
}