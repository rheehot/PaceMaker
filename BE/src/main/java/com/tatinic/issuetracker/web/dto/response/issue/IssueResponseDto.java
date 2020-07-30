package com.tatinic.issuetracker.web.dto.response.issue;

import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.domain.comment.Comment;
import com.tatinic.issuetracker.domain.issue.Issue;
import com.tatinic.issuetracker.domain.issue.IssueStatus;
import com.tatinic.issuetracker.domain.label.Label;
import com.tatinic.issuetracker.domain.milestone.Milestone;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IssueResponseDto {

    private Long id;
    private String title;
    private List<Comment> comments;
    private List<Label> labels;
    private List<Account> assignees;
    private Account account;
    private Milestone milestone;
    private IssueStatus issueStatus;

    public static IssueResponseDto of(Issue savedIssue) {
        return IssueResponseDto.builder()
                .id(savedIssue.getId())
                .issueStatus(savedIssue.getStatus())
                .title(savedIssue.getTitle())
                .milestone(savedIssue.getMilestone())
                .comments(savedIssue.getComments())
                .labels(new ArrayList<>())
                .assignees(new ArrayList<>())
                .build();
    }
}
