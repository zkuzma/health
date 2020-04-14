package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-12 20:56
 */
public interface MenuService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    Menu findMenuById(Integer id);

    void edit(Menu menu);

    void delete(Integer id);

    List<Menu> findAll();
}
