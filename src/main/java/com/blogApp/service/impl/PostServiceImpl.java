package com.blogApp.service.impl;

import com.blogApp.entity.Post;
import com.blogApp.payload.PostDto;
import com.blogApp.repository.PostRepository;
import com.blogApp.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = convertDtoToEntity(postDto);
        Post postEntity = postRepo.save(post);
        PostDto dto = convertEntityToDto(postEntity);
        return dto;
    }

    @Override
    public List<PostDto> fetchAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Post convertDtoToEntity(PostDto postDto){
        Post post= new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(post.getContent());
        return post;
    }
    public PostDto convertEntityToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
