package com.rabbiter.market.service.member_management.member;

import com.rabbiter.market.domain.member_management.member.Member;
import com.rabbiter.market.qo.member_management.member.QueryMember;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IMemberService extends IService<Member> {
    Page<Member> queryPageByQo(QueryMember qo);

    void delMember(Long id);

    void saveMember(Member member);

    Member queryMemberById(Long id);

    void updateMember(Member member);

    Member queryMemberByPhone(String phone);
}
