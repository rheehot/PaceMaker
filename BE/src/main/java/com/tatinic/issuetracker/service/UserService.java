package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.domain.AccountRepository;
import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.web.login.GithubInformationOfUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountRepository accountRepository;

    public void save(GithubInformationOfUser githubInformationOfUser) {
        Account account = accountRepository
                .findByUserId(githubInformationOfUser.getUserId()).orElse(new Account());
        accountRepository.save(account);
    }
}
