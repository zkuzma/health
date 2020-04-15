package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.service.CheckItemService;
import com.itheima.service.MenuService;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 11:59
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;

    /**
     * 查询所有菜单
     * @return
     */

    @RequestMapping("/findAll")
    public Result findAll() {
        List<Menu> list = menuService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }

    }

    /**
     * 增加菜单
     * @param menu
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu) {
        try {
            menuService.add(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
        return new Result(true, MessageConstant.ADD_MENU_SUCCESS);

    }

    /**
     * 菜单分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = menuService.findPage(queryPageBean);

        return pageResult;

    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionById")
    public Result findPermissionById(Integer id) {

            Menu menu = menuService.findMenuById(id);
            if (menu != null ) {
                return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
            } else {
                return new Result(false, MessageConstant.QUERY_MENU_FAIL);
            }


    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            menuService.delete(id);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);

    }

    /*
    * 编辑权限
    * */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu) {
        try {
            menuService.edit(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);

    }

}
