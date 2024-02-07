package com.example.memotion.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestAssuredUtils {

    public static ExtractableResponse<Response> post(Object requestBody, String path) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> get(String path) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> patch(Object requestBody, String path) {
        return RestAssured
                .given().log().all().body(requestBody)
                .contentType(ContentType.JSON)
                .when().patch(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(String path) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(path)
                .then().log().all()
                .extract();
    }
}
