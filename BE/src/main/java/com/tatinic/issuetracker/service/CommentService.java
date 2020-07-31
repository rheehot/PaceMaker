package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.domain.AccountRepository;
import com.tatinic.issuetracker.domain.account.Account;
import com.tatinic.issuetracker.domain.comment.Comment;
import com.tatinic.issuetracker.domain.comment.CommentRepository;
import com.tatinic.issuetracker.domain.comment.Emoji;
import com.tatinic.issuetracker.domain.issue.Issue;
import com.tatinic.issuetracker.domain.issue.IssueRepository;
import com.tatinic.issuetracker.web.dto.request.comment.CommentRequestDto;
import com.tatinic.issuetracker.web.dto.response.comment.CommentResponseDto;
import com.tatinic.issuetracker.web.login.OauthEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;

    public CommentResponseDto create(Long issueId,
                                     CommentRequestDto commentRequestDto,
                                     HttpServletRequest request) {

        String loginUserId = getLoginUserId(request);
        Account savedAccount = accountRepository.findByUserId(loginUserId).orElse(new Account());
        commentRequestDto.addAccount(savedAccount);
        Issue savedIssue = issueRepository.findById(issueId).orElse(new Issue());
        Comment newComment = Comment.of(commentRequestDto);
        newComment.addEmojis(commentRequestDto);
        savedIssue.addComment(newComment);
        newComment.addIssue(savedIssue);
        issueRepository.save(savedIssue);
//        commentRepository.save(newComment);
        return CommentResponseDto.of(newComment);
    }

    private String getLoginUserId(HttpServletRequest request) {
        return (String) request.getAttribute(OauthEnum.USER_ID.getValue());
    }
}
