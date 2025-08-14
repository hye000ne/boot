package com.sinse.xmlapp.model.member;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    private MemberHandler memberHandler;

    public MemberService(MemberHandler memberHandler) {
        this.memberHandler = memberHandler;
    }

    // 파싱
    public List<Member> parse() throws Exception {
        // 스프링 부트 프로젝트의 정적자원을 먼저 접근
        // ClassPathResource 객체는 프로젝트 내의 resources 디렉토리를 가리킴
        ClassPathResource classPathResource = new ClassPathResource("static/member.xml");
        File file = classPathResource.getFile();

        // SAXParser 생성
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        
        // 파일 대상으로 파싱 시작
        saxParser.parse(file, memberHandler); // 동기 방식으로 파싱함

        return memberHandler.getMemberList();
    }
}
