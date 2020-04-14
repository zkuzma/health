package com.itheima.dao;

import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 12:11
 */
public interface CheckItemDao {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    List<CheckItem> selectByCondition(String queryString);

    Long findByCheckItem(Integer id);

    void delete(Integer id);

    CheckItem findCheckItemById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findCheckItemListById(Integer id);
}
