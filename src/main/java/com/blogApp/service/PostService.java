package com.blogApp.service;

import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse fetchAllPosts(int pageNum, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto,long id);

    void deletePost(long id);
}
