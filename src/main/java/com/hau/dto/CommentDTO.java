package com.hau.dto;

import com.hau.entity.ProductEntity;
import com.hau.entity.UserEntity;

import java.sql.Blob;


public class CommentDTO  extends AbstractDTO<CommentDTO>{
    Long userId;

    Long productId;
    private String productName;

    private String imgUrl;
    private int rating;
    private String name;

    private String review;
    private Blob imgReviewUrl;
    private int likeCount;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getImgReviewUrl() {
        return imgReviewUrl;
    }

    public void setImgReviewUrl(Blob imgReviewUrl) {
        this.imgReviewUrl = imgReviewUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
