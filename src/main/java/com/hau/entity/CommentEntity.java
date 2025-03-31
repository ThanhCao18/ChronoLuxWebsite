package com.hau.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "img_review_url")
    @Lob
    private Blob imgReviewUrl;
    @Column(nullable = false)
    private int rating;
    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String review;
    @Column(name = "likeCount",nullable = false)
    private int likeCount = 0;

    @ManyToMany(mappedBy = "commentsLike")
    private List<UserEntity> usersLike = new ArrayList<>();

    @OneToOne(mappedBy = "commentEntity", cascade = CascadeType.ALL)
    private Notification notification;

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

    public void setImgReviewUrl(Blob imgReviewUrl) {
        this.imgReviewUrl = imgReviewUrl;
    }

    public Blob getImgReviewUrl() {
        return imgReviewUrl;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
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

    public List<UserEntity> getUsersLike() {
        return usersLike;
    }

    public void setUsersLike(List<UserEntity> usersLike) {
        this.usersLike = usersLike;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
