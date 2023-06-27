package com.blogApp.service.impl;

import com.blogApp.entity.Comment;
import com.blogApp.entity.Post;
import com.blogApp.exception.ResourceNotFoundException;
import com.blogApp.payload.CommentDto;
import com.blogApp.repository.CommentRepository;
import com.blogApp.repository.PostRepository;
import com.blogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = convertDtoToComment(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepo.save(comment);
        return convertCommentToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment -> convertCommentToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Id", postId)
        );

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "commentId", commentId)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comment);
        return convertCommentToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId)
        );
        commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","id",commentId)
        );

        commentRepo.deleteById(commentId);

    }

    CommentDto convertCommentToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    Comment convertDtoToComment(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment=new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
