package com.tatinic.issuetracker.domain.issue;

import com.tatinic.issuetracker.domain.label.Label;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class LabeledIssue {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "label_id")
    private Label label;
}
