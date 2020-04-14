package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-02 13:52
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<CheckGroup> checkGroupList = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        PageInfo<CheckGroup> pageInfo=new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 根据id查找项目组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }


    /**
     * 查找项目组id对应的项目
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemByCheckGroup(Integer id) {
        return checkGroupDao.findCheckItemByCheckGroup(id);
    }



    /**
     *编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.edit(checkGroup);
        List<Integer> integerList = findCheckItemByCheckGroup(checkGroup.getId());
        deleteCheckItemById(integerList);

        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }


    /**
     * 删除检查组
     * @param id
     */
    @Override
    public void delete(Integer id) {
        List<Integer> checkItemByCheckGroup = findCheckItemByCheckGroup(id);
        Integer count = findSetmealAndCheckGroupCountByCheckGroupId(id);
        if (checkItemByCheckGroup!=null && checkItemByCheckGroup.size()>0 || count>0){
            throw new  RuntimeException(MessageConstant.DELETE_CHECKGROUP_LOSE);
        }
        checkGroupDao.delete(id);

    }

    /**
     * 查找检查组与套餐的关系
     * @param id
     * @return
     */
    public Integer findSetmealAndCheckGroupCountByCheckGroupId(Integer id){
        int i = checkGroupDao.findSetmealAndCheckGroupCountByCheckGroupId(id);
        return i;
    }

    /**
     * 删除检查组和检查项的关系
     */
    public void deleteCheckItemById(List<Integer> list){
        checkGroupDao.deleteCheckItemById(list);
    }




    /**
     * 添加项目组关联的项目
     * @param id
     * @param checkitemIds
     */
    private void setCheckGroupAndCheckItem(Integer id, Integer[] checkitemIds) {

        Map<String,Integer> map=new HashMap<>();
        map.put("checkgroup_id",id);
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitem_id",checkitemId);

            checkGroupDao.setCheckGroupAndCheckItem(map);
        }
    }


}
