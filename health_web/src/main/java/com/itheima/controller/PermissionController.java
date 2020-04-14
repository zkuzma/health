package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;
import com.itheima.service.CheckItemService;
import com.itheima.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 11:59
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    PermissionService permissionService;
    @Reference
    CheckItemService checkItemService;

    /**
     * 查询所有权限
     * @return
     */

    @RequestMapping("/findAll")
    public Result findAll() {
        List<Permission> list = permissionService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }

    }

    /**
     * 增加权限
     * @param permission
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);

    }

    /**
     * 权限分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = permissionService.findPage(queryPageBean);

        return pageResult;

    }

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionById")
    public Result findPermissionById(Integer id) {

            Permission permission = permissionService.findPermissionById(id);
            if (permission != null ) {
                return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
            } else {
                return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
            }


    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            permissionService.delete(id);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);

    }

    /*
    * 编辑权限
    * */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionService.edit(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);

    }
}
