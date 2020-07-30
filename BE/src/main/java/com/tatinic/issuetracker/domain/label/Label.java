package com.tatinic.issuetracker.domain.label;

import com.tatinic.issuetracker.domain.issue.LabeledIssue;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Label {

    @Id
    @GeneratedValue
    private Long id;

    private String color;

    @OneToMany(mappedBy = "label", cascade = CascadeType.ALL)
    private final List<LabeledIssue> labeledIssues = new ArrayList<>();
}
