package com.tatinic.issuetracker.domain.comment;

import com.tatinic.issuetracker.domain.issue.Issue;
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
    private Issue issue;

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
    private List<Emoji> emojis = new ArrayList<>();

}
