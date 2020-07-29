package com.tatinic.issuetracker.domain;

import com.tatinic.issuetracker.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String userEmail);
}
