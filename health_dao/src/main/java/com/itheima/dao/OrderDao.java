package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-08 17:49
 */
public interface OrderDao {
    List<Order> findByCondition(Order order);
//添加一条记录到体检预约表
    void add(Order order2);

    //预约成功页面展示
    Map findById(Integer id);
    //今日预约数
    Integer findCountOrderByToday(String today);
    //根据日期查询预约数
    Integer findCountMemberByDate(Map<String, Object> weekMap);
    //今日到诊数
    Integer findVisitCountOrderByToday(String today);
    //日期内到诊数
    Integer findVisitCountMemberByDate(Map<String, Object> weekMap);

    List<Map<String, Object>> findHotSetmeal();
}
