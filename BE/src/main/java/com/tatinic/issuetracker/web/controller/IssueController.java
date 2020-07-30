package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.domain.issue.Issue;
import com.tatinic.issuetracker.service.IssueService;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import com.tatinic.issuetracker.web.dto.response.issue.IssueResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("issue")
@RestController
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public IssueResponseDto createIssue(@RequestBody IssueRequestDto issueRequestDto,
                                        HttpServletRequest request) {
        return issueService.createIssue(issueRequestDto, request);
    }
}
