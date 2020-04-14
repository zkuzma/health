package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-11 13:24
 */
public interface MenuDao {


    List<Menu> findPage(String queryString);

    void add(Menu menu);

    Menu findMenuById(Integer id);

    void edit(Menu menu);

    Integer findRoleCountWithMenuId(Integer id);

    void delete(Integer id);

    List<Menu> findAll();

}
