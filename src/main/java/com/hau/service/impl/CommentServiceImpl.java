package com.hau.service.impl;

import com.hau.constant.SystemConstant;
import com.hau.converter.CommentConverter;
import com.hau.converter.Converter;
import com.hau.dto.CommentDTO;
import com.hau.dto.UserDTO;
import com.hau.entity.CommentEntity;
import com.hau.entity.UserEntity;
import com.hau.repository.CommentRepository;
import com.hau.repository.ProductRepository;
import com.hau.repository.UserRepository;
import com.hau.service.CommentService;
import com.hau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository  commentRepository;
    @Autowired
    private Converter<CommentDTO, CommentEntity> commentConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        CommentEntity commentEntity = commentConverter.convertToEntity(commentDTO);
       String userName = SecurityContextHolder.getContext().getAuthentication().getName();
       UserEntity user =  userRepository.findOneByUserNameAndStatus(userName, SystemConstant.ACTIVE_STATUS);
        commentEntity.setUser(user);

       commentEntity.setProduct(productRepository.findByIdAndActive(commentDTO.getProductId(),true));
       CommentEntity comment = commentRepository.save(commentEntity);// qua controller xu ly them phan id product o view
        return commentConverter.convertToDTO(comment);
    }

    @Override
    public List<CommentDTO> findByProductId(Long id) {
        return commentRepository.findByProductId(id)
                .orElse(Collections.emptyList())
                .stream()
                .map(commentEntity -> commentConverter.convertToDTO(commentEntity)
         ).toList();
    }

    @Override
    public List<CommentDTO> getByProductIdOrderByCreateDateDesc(Long id) {
        return commentRepository.findByProductIdOrderByCreateDateDesc(id)
                .orElse(Collections.emptyList())
                .stream()
                .map(commentEntity -> commentConverter.convertToDTO(commentEntity)
         ).toList();
    }

    @Override
    public CommentDTO findById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() ->  new RuntimeException("không tìm thấy bình luận nào"));
        return commentConverter.convertToDTO(commentEntity);
    }

    @Override
    public double calculateAverageRating(Long productId) {
       return  commentRepository.findByProductId(productId).orElse(Collections.emptyList()). stream()
                .mapToInt(CommentEntity::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    @Transactional
    public int Like(Long commentId,Long userId) {
        UserEntity user = userRepository.findOne(userId);

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (user.getCommentsLike().contains(commentEntity)) {
            // Nếu đã like → Unlike (xóa khỏi danh sách)
            user.getCommentsLike().remove(commentEntity);
            userRepository.save(user);
            commentEntity.setLikeCount(commentEntity.getLikeCount()-1);

        } else {
            // Nếu chưa like → Thêm like
            user.getCommentsLike().add(commentEntity);
            userRepository.save(user);
            commentEntity.setLikeCount(commentEntity.getLikeCount()+1);
        }

        return commentRepository.save(commentEntity).getLikeCount();
    }

    @Override
    @Transactional
    public boolean isLike(Long commentId, Long userId) {
        UserEntity user = userRepository.findOne(userId);
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return user.getCommentsLike().contains(commentEntity);
    }

    @Override
    public Integer countRating(Long productId) {
        return commentRepository.countByProductId(productId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bình luận"));
        for (UserEntity user : commentEntity.getUsersLike()) {
            user.getCommentsLike().remove(commentEntity);
        }

        commentEntity.getUsersLike().clear();
        commentRepository.save(commentEntity);
        commentRepository.deleteById(id);
    }
}
