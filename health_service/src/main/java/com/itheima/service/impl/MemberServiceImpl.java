package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-09 15:52
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;
    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

//    根据月统计会员数量
    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer>integerlist=new ArrayList<>();
        for (String month : list) {
            month=month+"-31";
           int count= memberDao.findMemberCountByMonth(month);
           integerlist.add(count);
        }
        return integerlist;
    }
}
