package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-13 19:38
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {

        //今日日期
        String today= DateUtils.parseDate2String(new Date());
        //今天新增会员数
        Integer todayNewMember= memberDao.findCountMemberByToday(today);
        //总会员数
        Integer totalMember=memberDao.findAllCountMember();
        //这周新增加会员数
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String sundayOfThisWeek = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        Map<String,Object>weekMap=new HashMap<>();
        weekMap.put("begin",thisWeekMonday);
        weekMap.put("end",sundayOfThisWeek);
        Integer thisWeekNewMember=memberDao.findCountMemberByDate(weekMap);
        // 获取本月最后一天的日期
        String lastDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
        // 获得本月第一天的日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        Map<String,Object>monthMap=new HashMap<>();
        monthMap.put("begin",firstDay4ThisMonth);
        monthMap.put("end",lastDay4ThisMonth);
        Integer thisMonthNewMember = memberDao.findCountMemberByDate(monthMap);
        //今日预约数
        Integer todayOrderNumber= orderDao.findCountOrderByToday(today);
        //本周预约数
        Integer thisWeekOrderNumber=orderDao.findCountMemberByDate(weekMap);
        //本月预约数
        Integer thisMonthOrderNumber=orderDao.findCountMemberByDate(monthMap);
        //今日到诊数
        Integer todayVisitsNumber= orderDao.findVisitCountOrderByToday(today);
        //本周到诊数
        Integer thisWeekVisitsNumber=orderDao.findVisitCountMemberByDate(weekMap);
        //本月到诊数
        Integer thisMonthVisitsNumber=orderDao.findVisitCountMemberByDate(monthMap);

        List<Map<String,Object>>hotSetmeal=orderDao.findHotSetmeal();
        Map<String,Object>result=new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
       result.put("hotSetmeal",hotSetmeal);
        return result;
    }
}
