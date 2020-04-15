package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.service.CheckGroupService;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-02 13:16
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    CheckGroupService checkGroupService;
    @Reference
    RoleService roleService;
    @RequestMapping("/add")
    public Result add(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds) {
        try {
            System.out.println("1");
            System.out.println("1");
            System.out.println("1");
            System.out.println("1");
//            roleService.add(role,menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    /**
     * 分页查询角色
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = roleService.findPage(queryPageBean);

        return pageResult;

    }

    /**
     * 根据角色id查询角色
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findCheckItemById(Integer id) {

       Role role = roleService.findById(id);
        if (role != null ) {
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
        } else {
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }



    }

    /**
     * 根据角色id查询关联的权限
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionIdsByRoleId")
    public List<Integer> findCheckItemByCheckGroup(Integer id) {

        List<Integer> list = roleService.findPermissionIdsByRoleId(id);
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }
    /**
     * 根据角色id查询关联的权限
     * @param id
     * @return
     */
    @RequestMapping("/findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id) {

        List<Integer> list = roleService.findMenuIdsByRoleId(id);
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }

    /**
     * 删除检查组
     * @param id
     * @return
     */

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkGroupService.delete(id);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);

    }

}
