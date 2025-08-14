package com.sinse.xmlapiapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class SpcdeInfo {
    private String locdate;
    private String seq;
    private String dateKind;
    private String isHoliday;
    private String dateName;
}
