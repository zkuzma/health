package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-05 17:45
 */
public interface OrdersettingService {
    void add(List<OrderSetting> orderSettinglist);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
