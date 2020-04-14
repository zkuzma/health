package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-03 13:11
 */
public interface SetMealService {
    List<CheckGroup> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    void delete(Integer id);

    Setmeal findSetmealById(Integer id);

    List<Setmeal> findAllSetmeal();

    int findCheckGroupBySetmealId(Integer id);

    List<Integer> findCheckGroupWithSetmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);


    List<Map<String, Object>> getSetmealReport();
}
