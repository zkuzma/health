package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-11 13:04
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public User findUserByUsername(String username) {

        return userDao.findUserByUsername(username);
    }

    //分页查询用户
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<User> checkGroupList = userDao.findPage(queryPageBean.getQueryString());
        PageInfo<User> pageInfo=new PageInfo<>(checkGroupList);
        List<User> list = pageInfo.getList();
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public User findUserById(Integer id) {
       return  userDao.findUserById(id);

    }

    @Override
    public List<Integer> findCountRoleByUserId(Integer id) {
        return userDao.findCountRoleByUserId(id);
    }

    //添加用户
    @Override
    public void add(User user, Integer[] roleIds) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        userDao.add(user);
        setUserIdAndRoleID(user.getId(),roleIds);
    }
    //编辑用户
    @Override
    public void edit(User user, Integer[] roleIds) {
        List<Integer> integerList = userDao.findCountRoleByUserId(user.getId());
        if (integerList.size()>0&&integerList!=null){
            //删除用户关联的角色
            userDao.deleteRoleByUserId(user.getId());
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userDao.edit(user);
        if (roleIds!=null){
            setUserIdAndRoleID(user.getId(),roleIds);
        }
    }

    @Override
    public void delete(Integer id) {
        List<Integer> countRoleByUserId = userDao.findCountRoleByUserId(id);
        if (countRoleByUserId.size()>0&&countRoleByUserId!=null){
            throw new RuntimeException(MessageConstant.DELETE_USER_LOSE);
        }
        userDao.delete(id);
    }

    //设置关联的用户和角色
    private void setUserIdAndRoleID(Integer id,Integer[] roleIds) {
        Map<String,Object>map=new HashMap<>();
        map.put("user_id",id);
        if (roleIds!=null){
            for (Integer roleId : roleIds) {
                map.put("role_id",roleId);
                userDao.setUserIdAndRoleID(map);

            }
        }
    }
}
