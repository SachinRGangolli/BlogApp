package com.blogApp.service;

import com.blogApp.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> fetchAllPosts();
}
