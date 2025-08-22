package com.sinse.chatroomapp.service;

import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.exception.MemberException;
import com.sinse.chatroomapp.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Override
    public List<Member> selectAll() {
        return memberMapper.selectAll();
    }

    @Override
    public Member login(String id, String pwd) {
        Member member = new Member();
        member.setId(id);
        member.setPwd(pwd);
        Member login = memberMapper.login(member);

        if(login == null) throw new MemberException("아이디와 비밀번호를 다시 입력해주세요.");

        return login;
    }
}
