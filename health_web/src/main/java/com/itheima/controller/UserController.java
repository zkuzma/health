package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-11 16:21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    UserService userService;



    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
    //分页查询用户
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = userService.findPage(queryPageBean);

        return pageResult;

    }
    //根据用户id查询用户
    @RequestMapping("/findUserById")
    public Result findCheckItemById(Integer id) {

        com.itheima.pojo.User user = userService.findUserById(id);
        if (user != null ) {
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
        } else {
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }



    }

    @RequestMapping("/findCountRoleByUserId")
    public List<Integer> findCountRoleByUserId(Integer id) {

        List<Integer> list = userService.findCountRoleByUserId(id);
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }
    @RequestMapping("/add")
    public Result add(@RequestBody com.itheima.pojo.User user, Integer[] roleIds) {
        try {
            userService.add(user,roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);

    }
    /**
     * 编辑用户
     * @param user
     *
     * @param roleIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.itheima.pojo.User user, Integer[] roleIds) {
        try {
            userService.edit(user,roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);

    }
    @RequestMapping("delete")
    public Result delete(Integer id){
        try {
            userService.delete(id);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    };
}
