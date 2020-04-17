package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.User;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-11 13:01
 */
public interface UserService {
    User findUserByUsername(String username);

    PageResult findPage(QueryPageBean queryPageBean);

    User findUserById(Integer id);

    List<Integer> findCountRoleByUserId(Integer id);

    void add(User user, Integer[] roleIds);

    void edit(User user, Integer[] roleIds);

    void delete(Integer id);
}
