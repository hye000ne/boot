package com.sinse.chatroomapp.controller;


import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.exception.MemberException;
import com.sinse.chatroomapp.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(String id, String pwd, Model model, HttpSession session){

        try {
            Member member = memberService.login(id, pwd);
            session.setAttribute("member", member);
        } catch (MemberException e) {
            log.error(e.getMessage());
            model.addAttribute("msg", e.getMessage());
            return "member/login";
        }

        return "redirect:/chat/main";
    }
}
