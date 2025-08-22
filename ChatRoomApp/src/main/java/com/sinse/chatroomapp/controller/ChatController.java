package com.sinse.chatroomapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        if(session.getAttribute("member")==null){
            model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
            return "/member/login";
        }
        return "chat/main";
    }
}
