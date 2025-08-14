package com.sinse.busankidscafe.model;

import lombok.Data;

// 데이터를 담을 POJO
@Data
public class KidsCafe {
    private String gugun;
    private String cafe_nm;
    private String road_nm;
    private String tel_no;
    private double lat;
    private double lng;
    private String data_date;
}
