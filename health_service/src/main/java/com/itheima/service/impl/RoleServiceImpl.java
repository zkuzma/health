package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Role;
import com.itheima.service.CheckGroupService;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-02 13:52
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;


    /**
     * 分页查询角色
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Role> list = roleDao.findPage(queryPageBean.getQueryString());
        PageInfo<Role> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());    }

    /**
     * 根据角色id查询角色
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    /**
     * 根据角色id查询关联的权限
     * @param id
     * @return
     */
    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    /**
     * 根据角色id查询关联的菜单
     * @param id
     * @return
     */
    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    /**
     * 增加角色
     * @param role
     * @param menuIds
     * @param permissionIds
     */
    //添加角色
    @Override
    public void add(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.add(role);
        //添加角色和菜单的关系
        setRoleAndMenu(role.getId(),menuIds);
        //添加角色和权限的关系
        setRoleAndpermission(role.getId(),permissionIds);
    }

    /**
     * 编辑角色
     * @param role
     * @param menuIds
     * @param permissionIds
     */
    @Override
    public void edit(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.edit(role);
        //根据角色id查询关系菜单的数量
        Integer countRole=roleDao.findCountMenuByRoleId(role.getId());
        //根据角色id查询关系菜单的数量
        Integer countPermission=roleDao.findCountPermissionByRoleId(role.getId());
        if (countRole>0){
            //删除所有角色和菜单的关系
            roleDao.deleteCountMenuByRoleId(role.getId());
        }
        if (countPermission>0){
            //删除所有角色和权限的关系
            roleDao.deleteCountPermissionByRoleId(role.getId());

        }

        if (menuIds.length>0&&menuIds!=null){

            setRoleAndMenu(role.getId(),menuIds);
        }
        if (permissionIds.length>0&&permissionIds!=null){

            setRoleAndpermission(role.getId(),permissionIds);
        }

    }
    //删除角色
    @Override
    public void delete(Integer id) {
        Integer countMenu = roleDao.findCountMenuByRoleId(id);
        if (countMenu>0){
            throw new RuntimeException(MessageConstant.DELETE_ROLE_LOSE);

        }
        Integer countPermission = roleDao.findCountPermissionByRoleId(id);
        if (countPermission>0){
            throw new RuntimeException(MessageConstant.DELETE_ROLE_LOSE);
        }
        roleDao.delete(id);

    }

    private void setRoleAndpermission(Integer id, Integer[] permissionIds) {
        Map<String,Object>map=new HashMap<>();
        map.put("role_id",id);
        for (Integer permissionId : permissionIds) {
            map.put("permission_id",permissionId);
            roleDao.setRoleAndPermission(map);
        }
    }

    private void setRoleAndMenu(Integer id, Integer[] menuIds) {
        Map<String,Object>map=new HashMap<>();
        map.put("role_id",id);
        for (Integer menuId : menuIds) {
            map.put("menu_id",menuId);
            roleDao.setRoleAndMenu(map);
        }

    }
}
