package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.domain.AccountRepository;
import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.domain.comment.Comment;
import com.tatinic.issuetracker.domain.issue.Issue;
import com.tatinic.issuetracker.domain.issue.IssueRepository;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import com.tatinic.issuetracker.web.dto.request.issue.IssueStatusRequestDto;
import com.tatinic.issuetracker.web.dto.request.issue.IssueUpdateRequestDto;
import com.tatinic.issuetracker.web.dto.response.issue.IssueResponseDto;
import com.tatinic.issuetracker.web.dto.response.issue.SeveralIssueResponseDto;
import com.tatinic.issuetracker.web.login.OauthEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;

    public IssueResponseDto createIssue(IssueRequestDto issueRequestDto, HttpServletRequest request) {
        String loginUserId = getLoginUserId(request);
        Account savedAccount = accountRepository.findByUserId(loginUserId).orElse(new Account());
        issueRequestDto.addAccount(savedAccount);
        Issue newIssue = Issue.of(issueRequestDto);
        Comment newComment = Comment.of(issueRequestDto);

        newIssue.addComment(newComment);
        newComment.addIssue(newIssue);
        Issue savedIssue = issueRepository.save(newIssue);

        return IssueResponseDto.builder()
                .id(savedIssue.getId())
                .account(issueRequestDto.getAccount())
                .assignees(new ArrayList<>())
                .labels(new ArrayList<>())
                .comments(savedIssue.getComments())
                .milestone(savedIssue.getMilestone())
                .title(issueRequestDto.getTitle())
                .issueStatus(newIssue.getStatus())
                .build();
    }

    public SeveralIssueResponseDto changeStatus(IssueStatusRequestDto issueStatusRequestDto, HttpServletRequest request) {
        String loginUserId = getLoginUserId(request);
        Account savedAccount = accountRepository.findByUserId(loginUserId).orElse(new Account());

        List<IssueResponseDto> data = changeStatusFromEachIssue(issueStatusRequestDto);

        return SeveralIssueResponseDto.builder()
                .data(data)
                .build();
    }

    private List<IssueResponseDto> changeStatusFromEachIssue(IssueStatusRequestDto issueStatusRequestDto) {
        List<IssueResponseDto> data = new ArrayList<>();
        for (long issueId : issueStatusRequestDto.getIssueIds()) {
            Issue savedIssue = issueRepository.findById(issueId).orElse(new Issue());
            savedIssue.changeStatus();
            data.add(IssueResponseDto.of(savedIssue));
        }
        return data;
    }

    private String getLoginUserId(HttpServletRequest request) {
        return (String) request.getAttribute(OauthEnum.USER_ID.getValue());
    }

    public IssueResponseDto updateIssue(Long issueId,
                                        IssueUpdateRequestDto issueUpdateRequestDto,
                                        HttpServletRequest request) {
        Issue savedIssue = issueRepository.findById(issueId).orElse(new Issue());
//        String loginUserId = getLoginUserId(request);
        String loginUserId = "guswns1659";
        if (savedIssue.isLoginUser(loginUserId)) {
            savedIssue.update(issueUpdateRequestDto);
            return IssueResponseDto.of(issueRepository.save(savedIssue));
        }
        throw new IllegalStateException("수정 권한이 없습니다.");
    }

    public IssueResponseDto findById(Long issueId) {
        Issue savedIssue = issueRepository.findById(issueId).orElse(new Issue());
        return IssueResponseDto.of(savedIssue);
    }

    public SeveralIssueResponseDto findAll() {
        List<Issue> issues = issueRepository.findAll();
        List<IssueResponseDto> data = issues.stream()
                .map(IssueResponseDto::of)
                .collect(Collectors.toList());

        return SeveralIssueResponseDto.builder()
                .data(data)
                .build();
    }
}
