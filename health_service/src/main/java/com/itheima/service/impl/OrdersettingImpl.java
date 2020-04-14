package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrdersettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-05 17:57
 */
@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingImpl implements OrdersettingService {
    @Autowired
    OrdersettingDao ordersettingDao;

    /**
     * 预约设置
     * @param orderSettinglist
     */
    @Override
    public void add(List<OrderSetting> orderSettinglist) {
        for (OrderSetting orderSetting : orderSettinglist) {
          int count=  ordersettingDao.findCountByOrderSettingDate(orderSetting.getOrderDate());
          if (count>0){
              ordersettingDao.updateOrdersetting(orderSetting);
          }else {
              ordersettingDao.addOrdersetting(orderSetting);
          }
        }
    }

    /**
     * 按月查询预约信息
     * @param date
     * @return
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        List<OrderSetting>orderSettingList=ordersettingDao.getOrderSettingByMonth(date);
        List<Map>list=new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map<String,Object>map=new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);

        }
        return list;
    }


    /**
     * 根据日期设置预约人数
     * @param orderSetting
     * @return
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = ordersettingDao.findCountByOrderSettingDate(orderSetting.getOrderDate());
        if (count>0){
            ordersettingDao.updateOrdersetting(orderSetting);
        }else {
            ordersettingDao.addOrdersetting(orderSetting);
        }

    }
}
