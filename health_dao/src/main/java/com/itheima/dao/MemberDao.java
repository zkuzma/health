package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-08 18:48
 */
public interface MemberDao {
    //根据手机号查询会员
    Member findMemberByTelephone(String telephone);

    //注册会员
    void add(Member member);
    //根据月统计会员数量
    Integer findMemberCountByMonth(String month);

    //今天新增会员数
    Integer findCountMemberByToday(String today);
    //总会员数
    Integer findAllCountMember();
    //根据日期查找会员数
    Integer findCountMemberByDate(Map<String, Object> weekMap);
}
