package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.service.CommentService;
import com.tatinic.issuetracker.web.dto.request.comment.CommentRequestDto;
import com.tatinic.issuetracker.web.dto.response.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{issueId}")
    public CommentResponseDto create(@PathVariable Long issueId,
                                     @RequestBody CommentRequestDto commentRequestDto,
                                     HttpServletRequest request) {
        return commentService.create(issueId, commentRequestDto, request);
    }
}
