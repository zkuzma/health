package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.service.MenuService;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 12:09
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;

    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Menu> list = menuDao.findPage(queryPageBean.getQueryString());
        PageInfo<Menu> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 增加菜单
     * @param menu
     * @return
     */
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    /**
     * 编辑菜单
     * @param menu
     */
    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    public void delete(Integer id) {
        Integer count=menuDao.findRoleCountWithMenuId(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.DELETE_MENU_LOSE);
        }
        menuDao.delete(id);
    }

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }


}
