package com.haui.service;

import com.haui.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    void savePost(PostDTO postDTO);
    void deletePostById(Long id);
}

