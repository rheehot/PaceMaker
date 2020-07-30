package com.tatinic.issuetracker.domain.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.domain.comment.Comment;
import com.tatinic.issuetracker.domain.milestone.Milestone;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Issue {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private final List<LabeledIssue> labeledIssues = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private final List<Assignee> assignees = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    private Milestone milestone;

    public static Issue of(IssueRequestDto issueRequestDto) {
        return Issue.builder()
                .account(issueRequestDto.getAccount())
                .title(issueRequestDto.getTitle())
                .status(IssueStatus.OPEN)
                .build();
    }

    public void addComment(Comment newComment) {
        this.comments.add(newComment);
    }

    public void changeStatus() {
        if (this.status.equals(IssueStatus.OPEN)) {
            this.status = IssueStatus.CLOSED;
        } else {
            this.status = IssueStatus.OPEN;
        }
    }
}
