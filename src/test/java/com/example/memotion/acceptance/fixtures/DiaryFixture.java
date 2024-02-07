package com.example.memotion.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.example.memotion.utils.RestAssuredUtils.post;

public class DiaryFixture {
    private static final String BASE_PATH = "/diary";

    public static ExtractableResponse<Response> 다이어리_추가(Object requestBody) {
        return post(requestBody, BASE_PATH);
    }
}
