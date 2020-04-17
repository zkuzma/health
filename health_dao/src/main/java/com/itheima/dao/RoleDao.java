package com.itheima.dao;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-11 13:17
 */
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);

    List<Role> findPage(String queryString);

    Role findById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    //添加角色
    void add(Role role);

    //设置角色和菜单的关系
    void setRoleAndMenu(Map<String, Object> map);

    //设置角色和权限的关系
    void setRoleAndPermission(Map<String, Object> map);

    //编辑角色
    void edit(Role role);
    //根据角色id查询关系菜单的数量
    Integer findCountMenuByRoleId(Integer id);
    //根据角色id查询关系菜单的数量
    Integer findCountPermissionByRoleId(Integer id);
    //删除所有角色和菜单的关系
    void deleteCountMenuByRoleId(Integer id);
    //删除所有角色和权限的关系
    void deleteCountPermissionByRoleId(Integer id);

    void delete(Integer id);

    List<Role> findAll();
}
