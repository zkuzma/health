package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-09 19:14
 */
public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void add(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);
}
