package com.sinse.schoolapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<Body> body;
    private Header header;

    @Data
    public static class Body {
        @JsonProperty("YEAR")
        private String year;

        @JsonProperty("GARDEN_SE")
        private String garden_se;

        @JsonProperty("ENTSCH_TY_NM")
        private String entsch_typ_nm;

        @JsonProperty("DETAIL_ENTSCH_TY_NM")
        private String detail_entsch_ty_nm;

        @JsonProperty("HGSCHL_TY")
        private String hgschl_ty;

        @JsonProperty("DETAIL_TY")
        private String detail_ty;

        @JsonProperty("STDNT_CO")
        private int stdnt_co;
    }

    @Data
    public static class Header {
        private int perPage;
        private String resultCode;
        private int totalRows;
        private int currentPage;
        private String resultMsg;
    }
}
