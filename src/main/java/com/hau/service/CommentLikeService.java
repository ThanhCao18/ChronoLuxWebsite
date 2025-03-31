package com.hau.service;

public interface CommentLikeService {
    void like(int user_id, int comment_id);
    boolean isLike(int user_id, int comment_id);
}
