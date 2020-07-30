package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.domain.issue.IssueStatus;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import com.tatinic.issuetracker.web.dto.request.issue.IssueStatusRequestDto;
import com.tatinic.issuetracker.web.dto.response.issue.IssueResponseDto;
import com.tatinic.issuetracker.web.dto.response.issue.SeveralIssueResponseDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "30000")
public class IssueControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private final static String requestMapping = "/issue";
    private final static String JWT_TOKEN = "jwt";
    private final static String LOCALHOST = "http://localhost:";

    @CsvSource({"issueTitle, commentContent"})
    @ParameterizedTest
    void 이슈생성API를_테스트한다(String title, String commentContent) {

        String url = LOCALHOST + port + requestMapping;

        IssueRequestDto issueRequestDto = IssueRequestDto.builder()
                .title(title)
                .commentContent(commentContent)
                .build();

        IssueResponseDto issueResponseDto = webTestClient.post()
                .uri(url)
                .header(OauthEnum.AUTHORIZATION.getValue(), JWT_TOKEN)
                .body(Mono.just(issueRequestDto), IssueRequestDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(IssueResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(issueResponseDto.getId()).isEqualTo(2);
        assertThat(issueResponseDto.getComments().get(0).getId()).isEqualTo(1);
        assertThat(issueResponseDto.getIssueStatus()).isEqualTo(IssueStatus.OPEN);
    }

    @CsvSource({"1, 2, 3"})
    @ParameterizedTest
    void 이슈상태를_열고닫는_API를_테스트한다(Long one, Long two, Long three) {

        String url = LOCALHOST + port + requestMapping + "/changeStatus";

        List<Long> issueIds = new ArrayList<>();
        issueIds.add(one);
        issueIds.add(two);
        issueIds.add(three);

        IssueStatusRequestDto issueStatusRequestDto = IssueStatusRequestDto.builder()
                .issueIds(issueIds)
                .build();

        SeveralIssueResponseDto severalIssueResponseDto = webTestClient.put()
                .uri(url)
                .header(OauthEnum.AUTHORIZATION.getValue(), JWT_TOKEN)
                .body(Mono.just(issueStatusRequestDto), IssueStatusRequestDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(SeveralIssueResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(severalIssueResponseDto.getData().get(0).getIssueStatus()).isEqualTo(IssueStatus.CLOSED);
    }
}
