package com.hau.service;

import com.hau.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO save(CommentDTO commentDTO);
    List<CommentDTO> findByProductId(Long id);

    List<CommentDTO> getByProductIdOrderByCreateDateDesc(Long id);
    CommentDTO findById(Long id);
    double calculateAverageRating(Long productId);
    int Like(Long commentId,Long userId) ;
    boolean isLike(Long commentId,Long userId);
    Integer countRating(Long productId);
    void delete(Long id);
}
