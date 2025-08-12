package com.sinse.schoolapp.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.schoolapp.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class SchoolController {
    private final String serviceKey = "SMTLY0H0fzSJzseF%2FFjToXtAkjAff8JdXP24D93wn6fAtMwJpoCyjqTuf5Kh%2FjJDWnLl0xJdsaot5ZtwLaiRhQ%3D%3D";

    @GetMapping("/schools")
    public ApiResponse list() throws IOException, InterruptedException {
        String url = "http://apis.data.go.kr/6430000/freshmenHighTypeService/getFreshmenHighType?serviceKey=" + serviceKey +
                "&currentPage="+URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&perPage="+URLEncoder.encode("20", StandardCharsets.UTF_8);
                //"&YEAR="+URLEncoder.encode("2020", StandardCharsets.UTF_8) +
                //"&GARDEN_SE="+URLEncoder.encode("정원내", StandardCharsets.UTF_8) +
                //"&ENTSCH_TY_NM="+URLEncoder.encode("일반전형", StandardCharsets.UTF_8) +
                //"&DETAIL_ENTSCH_TY_NM="+URLEncoder.encode("일반전형", StandardCharsets.UTF_8) +
                //"&HGSCHL_TY="+URLEncoder.encode("일반고", StandardCharsets.UTF_8) +
                //"&DETAIL_TY="+URLEncoder.encode("null", StandardCharsets.UTF_8) +
                //"&STDNT_CO="+URLEncoder.encode("200", StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                return mapper.readValue(response.body(), ApiResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse(); // 빈 결과 반환
            }
        } else {
            System.err.println("API 요청 실패");
            return new ApiResponse();
        }
    }
}
