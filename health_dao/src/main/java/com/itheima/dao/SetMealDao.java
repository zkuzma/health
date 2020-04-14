package com.itheima.dao;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-03 13:12
 */
public interface SetMealDao {
    List<CheckGroup> findAll();

    List<Setmeal> findPage(String queryString);

    void add(Setmeal setmeal);

    void setCheckGroupAndSetmeal(Map<String, Integer> map);

    int findCheckGroupBySetmealId(Integer id);

    void delete(Integer id);

    List<Setmeal> findAllSetmeal();

    List<Integer> findCheckGroupWithSetmealId(Integer id);

    void deleteWithCheckGroup(Integer id);

    void edit(Setmeal setmeal);

    Setmeal findSetmealById(Integer id);

    List<Map<String, Object>> getSetmealReport();
}
