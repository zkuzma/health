package com.itheima.dao;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-11 13:17
 */
public interface RoleDao {
   Set<Role> findRolesByUserId(Integer userId);

   List<Role>findPage(String queryString);

    Role findById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);
}
