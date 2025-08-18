package com.sinse.chatroomapp.service;

import com.sinse.chatroomapp.domain.Member;

import java.util.List;

public interface MemberService {
    public List<Member> selectAll();
    public Member login(Member member);
}
