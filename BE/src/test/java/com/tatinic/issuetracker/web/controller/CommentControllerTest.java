package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.web.dto.request.comment.CommentRequestDto;
import com.tatinic.issuetracker.web.dto.response.comment.CommentResponseDto;
import com.tatinic.issuetracker.web.login.OauthEnum;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class CommentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private final static String requestMapping = "/comment";
    private final static String JWT_TOKEN = "jwt";
    private final static String LOCALHOST = "http://localhost:";

    @Transactional
    @CsvSource({"1, commentContent, 1"})
    @ParameterizedTest
    void 코멘트생성API를_테스트한다(Long issueId, String commentContent, Long commentId) {

        String requestUrl = LOCALHOST + port + requestMapping + "/" + issueId;

        List<String> emojis = Arrays.asList("THUMBS_UP", "THUMBS_DOWN");

        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content(commentContent)
                .emojis(emojis)
                .build();

        CommentResponseDto commentResponseDto = webTestClient.post()
                .uri(requestUrl)
                .header(OauthEnum.AUTHORIZATION.getValue(), JWT_TOKEN)
                .body(Mono.just(commentRequestDto), CommentRequestDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(CommentResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(commentResponseDto.getId()).isEqualTo(commentId);
        assertThat(commentResponseDto.getContent()).isEqualTo(commentContent);
    }
}
