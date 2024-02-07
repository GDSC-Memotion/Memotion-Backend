package com.example.memotion.acceptance;

import com.example.memotion.acceptance.fixtures.YoutubeFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class YoutubeAcceptanceTest extends AcceptanceTest{

    @DisplayName("hello")
    @Test
    public void 유튜브_API_검색_테스트() throws Exception {
        ExtractableResponse<Response> response = YoutubeFixture.유튜브_검색("when i feeling joy");
        response.body();
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
