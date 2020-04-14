package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrdersettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.CheckItemService;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-08 17:26
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrdersettingDao ordersettingDao;
    @Autowired
    MemberDao memberDao;
    @Override
    public Result order(Map map) {
        //查询预约日期是否经行了设置
        int count = 0;
        Date date=null;
        try {
            date = DateUtils.parseString2Date((String) map.get("orderDate"));
            count = ordersettingDao.findCountByOrderSettingDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        };
        if (count<=0){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //查询是否约满
        OrderSetting orderSetting=null;
        try {
            orderSetting = ordersettingDao.findOrdersettingByOrderDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orderSetting.getReservations()==orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //查询是否为会员
        Member member=memberDao.findMemberByTelephone((String) map.get("telephone"));
        //为空注册会员
        if (member==null){
            member=new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
//        查询是否重复预约
        if (member!=null){
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order>orderList=orderDao.findByCondition(order);
            if (orderList!=null && orderList.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }

        }
        //可以预约
        String orderType = (String) map.get("orderType");
        Order order2=new Order(member.getId(),date,orderType,Order.ORDERSTATUS_NO,Integer.parseInt((String) map.get("setmealId")));
        //添加一条记录到体检预约表
        orderDao.add(order2);

        //更新预约设置,预约人数加一
        ordersettingDao.updateReservations(date);


        return new Result(true, MessageConstant.ORDER_SUCCESS,order2);
    }

    /**
     * 预约成功页面展示
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) {
        Map map = orderDao.findById(id);
        if (map!=null){
            Date orderDate = (Date) map.get("orderDate");
            String date2String=null;
            try {
                date2String = DateUtils.parseDate2String(orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("orderDate",date2String);
        }
        return map;
    }
}
