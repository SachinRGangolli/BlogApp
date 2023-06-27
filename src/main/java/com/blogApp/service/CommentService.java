package com.blogApp.service;

import com.blogApp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);
}
