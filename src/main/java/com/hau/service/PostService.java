package com.hau.service;

import com.hau.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    void savePost(PostDTO postDTO);
    void deletePostById(Long id);
}

