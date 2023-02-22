package com.mapper;

import com.entity.Flour;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


public interface FlourMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Flour record);

    int insertSelective(Flour record);

    Flour selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Flour record);

    int updateByPrimaryKey(Flour record);
    List<Flour> selectAll();
    List<Flour> selectByPage(Map map);
    int selectCount();
    List<Flour> selectByLikeName(String name);
}