package com.service.impl;

import com.entity.Flour;
import com.entity.Page;
import com.mapper.FlourMapper;
import com.service.FlourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class FlourServiceImpl implements FlourService {
    @Autowired
    FlourMapper flourMapper;
    @Override
    public List<Flour> inqure() {
        return flourMapper.selectAll();
    }

    @Override
    public Page<Flour> selectPage(Page<Flour> page) {
        //第一页开始，则从第零条开始查
        HashMap<Object, Object> map = new HashMap<>();
        map.put("currentPage",(page.getCurrentPage()-1)*page.getLimitPage());
        map.put("limitPage",page.getLimitPage());
        //拿到返回的集合
        List<Flour> flours = flourMapper.selectByPage(map);
        //拿到总条数
        int totelCount = flourMapper.selectCount();
        page.setTotelCount(totelCount);
        //设置总页数
         int totlePage=totelCount/page.getLimitPage();
         int mod=totelCount%page.getLimitPage();
         if(mod==0){
             page.setTotlePage(totlePage);
         }else {
             page.setTotlePage(totlePage+1);
         }
        //设置数据
        page.setData(flours);
        return page;
    }

    @Override
    public int deleteFlour(Integer id) {
        return flourMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Flour> selectLike(String name) {
        List<Flour> flours = flourMapper.selectByLikeName(name);
        return flours;
    }

    @Override
    public int insertFlour(Flour flour) {
        return flourMapper.insert(flour);
    }

    @Override
    public int updateFlour(Flour flour1) {
        return flourMapper.updateByPrimaryKey(flour1);
    }


}
