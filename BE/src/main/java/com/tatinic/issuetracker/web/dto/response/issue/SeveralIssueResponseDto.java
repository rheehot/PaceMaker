package com.tatinic.issuetracker.web.dto.response.issue;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SeveralIssueResponseDto {

    private List<IssueResponseDto> data;
}
