package com.sinse.foodapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
//Open API로 부터 받은 응답 정보를 담기 위한 객체
public class ApiResponse {
    private List<Body> body;
    private Header header;

    // Body 내부 클래스 정의
    @Data
    public static class Body {
        @JsonProperty("SIGUN")
        private String sigun; // 관할 행정구역

        @JsonProperty("MNMNU")
        private String mnmnu; // 메인 메뉴

        @JsonProperty("SE")
        private String se; // 간단 설명

        @JsonProperty("CMPNM")
        private String cmpnm; // 상호명

        @JsonProperty("MENU")
        private String menu; // 메뉴

        @JsonProperty("TELNO")
        private String telno; // 연락처

        @JsonProperty("_URL")
        private String url; // 홈페이지

        @JsonProperty("ADRES")
        private String adres; // 주소

        @JsonProperty("LO")
        private double lo; // 위도

        @JsonProperty("LA")
        private double la; // 경도

        @JsonProperty("TIME")
        private String time; // 영업시간

        @JsonProperty("DC")
        private String dc; // 상세 설명
    }


    // Header 정의
    @Data
    public static class Header {
        @JsonProperty("perPage")
        private int perPage;

        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("totalRows")
        private int totalRows;

        @JsonProperty("currentPage")
        private int currentPage;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

}