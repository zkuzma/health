package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-05 17:58
 */
public interface OrdersettingDao {
    int findCountByOrderSettingDate(Date orderDate);

    void updateOrdersetting(OrderSetting orderSetting);

    void addOrdersetting(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String date);
//根据日期查找设置数据
    OrderSetting findOrdersettingByOrderDate(Date orderDate);

    void updateReservations(Date date);
}
