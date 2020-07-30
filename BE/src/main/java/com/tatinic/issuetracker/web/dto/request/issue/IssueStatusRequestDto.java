package com.tatinic.issuetracker.web.dto.request.issue;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IssueStatusRequestDto {

    private List<Long> issueIds;
}
