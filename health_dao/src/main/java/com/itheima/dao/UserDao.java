package com.itheima.dao;

import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-11 13:05
 */
public interface UserDao {
    User findUserByUsername(String username);

    List<User> findPage(String queryString);

    User findUserById(Integer id);

    List<Integer> findCountRoleByUserId(Integer id);
    //添加用户
    void add(User user);
    //设置关联的用户和角色
    void setUserIdAndRoleID(Map map);

    void deleteRoleByUserId(Integer id);

    void edit(User user);

    void delete(Integer id);
}
