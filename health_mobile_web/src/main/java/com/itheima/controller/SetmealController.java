package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-06 15:53
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetMealService setMealService;
    /**
     * 查询所有套餐
     * @return
     */
    @RequestMapping("/findAllSetmeal")
    public Result findAllSetmeal() {
        List<Setmeal> list = setMealService.findAllSetmeal();
        if (list != null && list.size()>0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    /**
     * 根据套餐id查询套餐及其关联的检查组和检查项数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal= setMealService.findSetmealById(id);
        if (setmeal != null ) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
}
