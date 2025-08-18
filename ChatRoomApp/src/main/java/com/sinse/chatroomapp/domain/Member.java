package com.sinse.chatroomapp.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Member {
    private int memberId;
    private String id;
    private String pwd;
    private String name;
    private String email;
    private Date regDate;
}
