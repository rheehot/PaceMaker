package com.tatinic.issuetracker.web.dto.request.issue;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IssueUpdateRequestDto {

    private String title;
}
