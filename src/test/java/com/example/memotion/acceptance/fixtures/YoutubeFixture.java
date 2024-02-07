package com.example.memotion.acceptance.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.example.memotion.utils.RestAssuredUtils.get;

public class YoutubeFixture {
    private static final String BASE_PATH = "/api/youtube";

    public static ExtractableResponse<Response> 유튜브_검색(String query) {
        return get(BASE_PATH + "?search=" + query);
    }
}
