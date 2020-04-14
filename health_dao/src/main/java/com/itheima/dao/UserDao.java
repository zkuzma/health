package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @auther 大雄
 * @create 2020-04-11 13:05
 */
public interface UserDao {
    User findUserByUsername(String username);
}
