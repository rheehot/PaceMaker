package com.tatinic.issuetracker.web.dto.request.issue;

import com.tatinic.issuetracker.domain.account.Account;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IssueRequestDto {

    private Account account;
    private String title;
    private String commentContent;

    public void addAccount(Account savedAccount) {
        this.account = savedAccount;
    }
}
