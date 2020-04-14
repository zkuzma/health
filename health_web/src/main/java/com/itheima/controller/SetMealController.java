package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * @auther 大雄
 * @create 2020-04-03 13:07
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    SetMealService setMealService;

    @Autowired
    JedisPool jedisPool;

    /**
     * 查找所有检查组
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> list = setMealService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    /**
     * 图片上传
     * @param imgFile
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam(value = "imgFile") MultipartFile imgFile){

        try {
            String originalFilename = imgFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(i);
            String pathName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),pathName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,pathName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,pathName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = setMealService.findPage(queryPageBean);

        return pageResult;

    }

    /**
     *增加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setMealService.add(setmeal,checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    /**
     * 删除套餐
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setMealService.delete(id);
        }catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);

    }

    /**
     * 根据套餐id查询套餐数据
     * @param id
     * @return
     */
    @RequestMapping("/findSetmealById")
    public Result findSetmealById(Integer id) {
       Setmeal setmeal = setMealService.findSetmealById(id);
        if (setmeal != null ) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    /**
     * 查询所有套餐
     * @return
     */
    @RequestMapping("/findAllSetmeal")
    public Result findAllSetmeal() {
        List<Setmeal>list = setMealService.findAllSetmeal();
        if (list != null && list.size()>0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }
    @RequestMapping("/findCheckGroupWithSetmealId")
    public Result findCheckGroupWithSetmealId(Integer id) {
        List<Integer>list = setMealService.findCheckGroupWithSetmealId(id);
        if (list != null && list.size()>0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        try {
            setMealService.edit(setmeal,checkgroupIds);
//            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES)
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }


}
