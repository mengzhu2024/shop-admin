package com.rabbiter.market.mapper.member_management.member;

import com.rabbiter.market.domain.member_management.member.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
