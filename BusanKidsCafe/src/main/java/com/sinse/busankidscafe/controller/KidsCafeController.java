package com.sinse.busankidscafe.controller;

import com.sinse.busankidscafe.api.ApiFetcher;
import com.sinse.busankidscafe.model.KidsCafe;
import com.sinse.busankidscafe.parser.KidsCafeHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class KidsCafeController {
    @GetMapping("/api/kids-cafes")
    public List<KidsCafe> getKidsCafe() throws Exception {
        String xml = new ApiFetcher().fetchKidsCafeXml(); // XML 문자열
        KidsCafeHandler handler = new KidsCafeHandler();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), handler);

        // Spring이 JSON으로 자동 변환
        return handler.getKidsCafes(); 
    }
}
