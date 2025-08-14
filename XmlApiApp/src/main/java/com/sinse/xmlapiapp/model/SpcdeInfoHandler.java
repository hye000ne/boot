package com.sinse.xmlapiapp.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SpcdeInfoHandler extends DefaultHandler {
    @Getter
    private List<SpcdeInfo> spcdeInfoList;
    private SpcdeInfo spcdeInfo;

    private boolean isDateKind;
    private boolean isDateName;
    private boolean isIsHoliday;
    private boolean isLocdate;
    private boolean isSeq;

    @Override
    public void startDocument() throws SAXException {
        spcdeInfoList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
        if (tag.equals("item")) spcdeInfo = new SpcdeInfo();
        else if (tag.equals("dateKind")) isDateKind = true;
        else if (tag.equals("dateName")) isDateName = true;
        else if (tag.equals("isHoliday")) isIsHoliday = true;
        else if (tag.equals("locdate")) isLocdate = true;
        else if (tag.equals("seq")) isSeq = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();
        if (content.isEmpty()) return;

        if(isDateKind) spcdeInfo.setDateKind(content);
        else if(isDateName) spcdeInfo.setDateName(content);
        else if(isIsHoliday) spcdeInfo.setIsHoliday(content);
        else if(isLocdate) spcdeInfo.setLocdate(content);
        else if(isSeq) spcdeInfo.setSeq(content);
    }

    @Override
    public void endElement(String uri, String localName, String tag) throws SAXException {
        if (tag.equals("item")) spcdeInfoList.add(spcdeInfo);
        else if (tag.equals("dateKind")) isDateKind = false;
        else if (tag.equals("dateName")) isDateName = false;
        else if (tag.equals("isHoliday")) isIsHoliday = false;
        else if (tag.equals("locdate")) isLocdate = false;
        else if (tag.equals("seq")) isSeq = false;
    }

    @Override
    public void endDocument() throws SAXException {
        log.debug("XML 파싱 완료, 리스트 크기: " + spcdeInfoList.size());
    }
}
