package com.itheima.dao;

import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-02 13:53
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    List<CheckGroup> selectByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemByCheckGroup(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteCheckItemById(List<Integer> list);

    void delete(Integer id);

    int findSetmealAndCheckGroupCountByCheckGroupId(Integer id);


    List<CheckGroup> findCheckGroupListById(Integer id);
}
