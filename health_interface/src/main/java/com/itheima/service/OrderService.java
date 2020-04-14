package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-08 17:20
 */
public interface OrderService {
    Result order(Map map);

    Map findById(Integer id);
}
