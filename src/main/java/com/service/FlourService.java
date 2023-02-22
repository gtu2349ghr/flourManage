package com.service;

import com.entity.Flour;
import com.entity.Page;
import com.mapper.FlourMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface FlourService {
    List<Flour>  inqure();
    Page<Flour> selectPage(Page<Flour> page);

    int deleteFlour(Integer id);

    List<Flour> selectLike(String name);

    int insertFlour(Flour flour);

    int updateFlour(Flour flour1);
}
