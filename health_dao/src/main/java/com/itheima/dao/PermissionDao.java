package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-11 13:24
 */
public interface PermissionDao {
    Set<Permission> findPermissionByRoleId(Integer roleId);

    /**
     * 权限分页查询
     * @param queryString
     * @return
     */
    List<CheckItem> selectByCondition(String queryString);

    /**
     * 新增权限
     * @param permission
     */
    void add(Permission permission);

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    Permission findPermissionById(Integer id);

    void edit(Permission permission);

    void delete(Integer id);

    int findRoleCountWithPermissionId(Integer id);

    List<Permission> findAll();
}
