package com.sinse.xmlapiapp.model;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SpcdeInfoService {
    private SpcdeInfoHandler spcdeInfoHandler;
    public SpcdeInfoService(SpcdeInfoHandler spcdeInfoHandler) {
        this.spcdeInfoHandler = spcdeInfoHandler;
    }

    public List<SpcdeInfo> parse(String xmlContent) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));

        saxParser.parse(inputStream, spcdeInfoHandler);

        return spcdeInfoHandler.getSpcdeInfoList();
    }
}
