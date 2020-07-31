package com.tatinic.issuetracker.web.dto.request.comment;

import com.tatinic.issuetracker.domain.account.Account;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommentRequestDto {

    private Account account;
    private String content;
    private List<String> emojis;

    public void addAccount(Account savedAccount) {
        this.account = savedAccount;
    }
}
