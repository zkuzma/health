package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrdersettingService;
import com.itheima.utils.POIUtils;
import com.itheima.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @auther 大雄
 * @create 2020-04-05 16:34
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {
    @Reference
    OrdersettingService ordersettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam(value = "excelFile") MultipartFile excelFile){

        try {
            System.out.println(excelFile.getOriginalFilename());
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list!=null && list.size()>0) {
                List<OrderSetting>orderSettinglist=new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(new Date(strings[0]));
                    orderSetting.setNumber(Integer.parseInt(strings[1]));
                    orderSettinglist.add(orderSetting);

                }
                ordersettingService.add(orderSettinglist);
            }
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {

            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {

        List<Map>list = ordersettingService.getOrderSettingByMonth(date);
        if (list != null ) {
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }



    }

    /**
     * 根据日期设置预约人数
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            ordersettingService.editNumberByDate(orderSetting);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);

    }
}
