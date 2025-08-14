package com.sinse.xmlapiapp.controller;

import com.sinse.xmlapiapp.model.SpcdeInfo;
import com.sinse.xmlapiapp.model.SpcdeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@Slf4j
public class SpcdeInfoController {
    private SpcdeInfoService spcdeInfoService;

    public SpcdeInfoController(SpcdeInfoService spcdeInfoService) {
        this.spcdeInfoService = spcdeInfoService;
    }

    @GetMapping("/spcdeInfos")
    public List<SpcdeInfo> getList() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getAnniversaryInfo"+
                "?serviceKey=SMTLY0H0fzSJzseF%2FFjToXtAkjAff8JdXP24D93wn6fAtMwJpoCyjqTuf5Kh%2FjJDWnLl0xJdsaot5ZtwLaiRhQ%3D%3D" +
                "&pageNo=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&numOfRows=" + URLEncoder.encode("20", StandardCharsets.UTF_8) +
                "&solYear=" + URLEncoder.encode("2025", StandardCharsets.UTF_8);
                /*"&solMonth=" + URLEncoder.encode("02", StandardCharsets.UTF_8);*/

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", "application/xml").GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200){
            log.debug(response.body());
            String xml = response.body();
            return spcdeInfoService.parse(xml);
        } else {
            System.err.println("API 요청 실패");
            return null;
        }
    }
}
