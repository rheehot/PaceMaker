package com.tatinic.issuetracker.domain.account;

import com.tatinic.issuetracker.domain.issue.Assignee;
import com.tatinic.issuetracker.domain.issue.Author;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String name;
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Assignee> assignees = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Author> authors = new ArrayList<>();
}
