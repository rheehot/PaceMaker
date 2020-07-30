package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.service.IssueService;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import com.tatinic.issuetracker.web.dto.request.issue.IssueStatusRequestDto;
import com.tatinic.issuetracker.web.dto.response.issue.IssueResponseDto;
import com.tatinic.issuetracker.web.dto.response.issue.SeveralIssueResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("changeStatus")
    public SeveralIssueResponseDto changeStatus(@RequestBody IssueStatusRequestDto issueStatusRequestDto,
                                                HttpServletRequest request) {
        return issueService.changeStatus(issueStatusRequestDto, request);
    }
}
