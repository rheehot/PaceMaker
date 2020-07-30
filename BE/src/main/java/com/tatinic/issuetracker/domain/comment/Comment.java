package com.tatinic.issuetracker.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.domain.issue.Issue;
import com.tatinic.issuetracker.web.dto.request.issue.IssueRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id")
    @JsonIgnore
    private Issue issue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    /* 엔젤해커톤 참고해서 아래와 같이 코드 작성함.
       차이점은 Enum타입을 List로 가지고 있는 형태.
       아래 설정으로 안되면 이슈트래커 코드 참고하기
       Emoij 객체에 @Embbedable 붙여야 하나 고민.
     */
    @ElementCollection
    @CollectionTable(name = "comment_emoij", joinColumns = @JoinColumn(name = "comment_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "unicode", column = @Column(name = "emoji_unicode")),
            @AttributeOverride(name = "name", column = @Column(name = "emoji_name"))
    })
    @Enumerated(EnumType.STRING)
    private final List<Emoji> emojis = new ArrayList<>();

    public static Comment of(IssueRequestDto issueRequestDto) {
        return Comment.builder()
                .content(issueRequestDto.getCommentContent())
                .account(issueRequestDto.getAccount())
                .build();
    }

    public void addIssue(Issue newIssue) {
        this.issue = newIssue;
    }
}
