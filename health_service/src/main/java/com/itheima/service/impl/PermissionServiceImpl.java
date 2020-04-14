package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;
import com.itheima.service.CheckItemService;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 12:09
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckItem> list = permissionDao.selectByCondition(queryPageBean.getQueryString());
        PageInfo<CheckItem> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 增加权限
     * @param permission
     * @return
     */
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionDao.findPermissionById(id);
    }

    /**
     * 编辑权限
     * @param permission
     */
    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    /**
     * 删除权限
     * @param id
     */
    @Override
    public void delete(Integer id) {
        int count=permissionDao.findRoleCountWithPermissionId(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.DELETE_PERMISSION_LOSE);
        }
        permissionDao.delete(id);
    }

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }


}
