package com.tatinic.issuetracker.domain.milestone;

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
public class Milestone {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    private final List<Issue> issues = new ArrayList<>();
}
