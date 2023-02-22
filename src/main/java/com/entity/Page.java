package com.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page <T>{
    private  Integer totlePage;
    private  Integer currentPage;
    private  Integer limitPage;
    private  Integer totelCount;
    private List<T> data;

}
