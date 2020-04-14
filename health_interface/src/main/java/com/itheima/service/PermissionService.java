package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-12 20:56
 */
public interface PermissionService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    Permission findPermissionById(Integer id);

    void edit(Permission permission);

    void delete(Integer id);

    List<Permission> findAll();
}
