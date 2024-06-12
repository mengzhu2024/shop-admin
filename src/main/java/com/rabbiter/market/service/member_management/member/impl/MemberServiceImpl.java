package com.rabbiter.market.service.member_management.member.impl;

import com.rabbiter.market.common.exception.BusinessException;
import com.rabbiter.market.domain.member_management.member.Member;
import com.rabbiter.market.mapper.member_management.member.MemberMapper;
import com.rabbiter.market.qo.member_management.member.QueryMember;
import com.rabbiter.market.service.member_management.member.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {
    @Override
    public Page<Member> queryPageByQo(QueryMember qo) {
        Page<Member> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getPhone()), "phone", qo.getPhone());
        wrapper.like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        super.page(page, wrapper);
        return page;
    }

    @Override
    public void delMember(Long id) {
        this.removeById(id);
    }

    @Override
    public void saveMember(Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", member.getPhone());
        Member one = super.getOne(wrapper);
        if (one != null) {
            throw new BusinessException("该用户已注册过");
        }
        member.setIntegral(0L);
        super.save(member);
    }

    @Override
    public Member queryMemberById(Long id) {
        return super.getById(id);
    }

    @Override
    public void updateMember(Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", member.getPhone())
                .ne("id",member.getId());
        Member one = super.getOne(wrapper);
        if (one != null) {
            throw new BusinessException("已被录入");
        }
        super.updateById(member);
    }

    @Override
    public Member queryMemberByPhone(String phone) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", phone);
        Member one = super.getOne(wrapper);
        if (one==null){
            throw new BusinessException("该会员不存在");
        }
        return one;
    }
}
