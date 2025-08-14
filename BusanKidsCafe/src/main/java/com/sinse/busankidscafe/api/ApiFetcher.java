package com.sinse.busankidscafe.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// API 호출 (java21)
@Slf4j
public class ApiFetcher {
    private String serviceKey = "SMTLY0H0fzSJzseF%2FFjToXtAkjAff8JdXP24D93wn6fAtMwJpoCyjqTuf5Kh%2FjJDWnLl0xJdsaot5ZtwLaiRhQ%3D%3D";

    public String fetchKidsCafeXml() throws Exception{
        log.info("serviceKey:{}",serviceKey);
        String url = "http://apis.data.go.kr/6260000/BusanKidsCafeInfoService/getKidsCafeInfo"
                + "?serviceKey=" + serviceKey
                + "&numOfRows=20"
                + "&pageNo=1"
                + "&resultType=xml";

        HttpClient client = HttpClient.newHttpClient();
        // GET 요청
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.debug(response.body());
        return  response.body(); // XML 문자열 반환
    }
}
