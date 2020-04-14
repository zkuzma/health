package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-03-31 12:09
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckServiceImpl implements CheckItemService {
    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckItem> list = checkItemDao.selectByCondition(queryPageBean.getQueryString());
        PageInfo<CheckItem> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void delete(Integer id) {
        Long count = checkItemDao.findByCheckItem(id);
        if (count > 0) {

            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_LOSE);
        }
        checkItemDao.delete(id);

    }

    @Override
    public CheckItem findCheckItemById(Integer id) {
        return checkItemDao.findCheckItemById(id);
    }

    /*
    * 编辑检查项
    * */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);

    }

}
