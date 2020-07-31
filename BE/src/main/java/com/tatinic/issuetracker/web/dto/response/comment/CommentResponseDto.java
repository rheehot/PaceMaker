package com.tatinic.issuetracker.web.dto.response.comment;

import com.tatinic.issuetracker.domain.comment.Comment;
import com.tatinic.issuetracker.domain.comment.Emoji;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private List<Emoji> emojis;

    public static CommentResponseDto of(Comment newComment) {
        return CommentResponseDto.builder()
                .id(newComment.getId())
                .content(newComment.getContent())
                .emojis(newComment.getEmojis())
                .build();
    }
}
