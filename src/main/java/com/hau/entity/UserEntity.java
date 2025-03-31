package com.hau.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "email")
    private String email;
    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "status")
    private int status;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;



    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "role_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<CommentEntity> ratings;

    @OneToMany(mappedBy = "user")
    private List<BillEntity> bills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private VoucherEntity voucher;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "comment_likes ",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> commentsLike = new ArrayList<>();

    @ManyToMany(mappedBy = "admins")
    private Set<Notification> notifications;

    public List<CommentEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<CommentEntity> ratings) {
        this.ratings = ratings;
    }

    public VoucherEntity getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherEntity voucher) {
        this.voucher = voucher;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<BillEntity> getBills() {
        return bills;
    }

    public void setBills(List<BillEntity> bills) {
        this.bills = bills;
    }

    public List<CommentEntity> getCommentsLike() {
        return commentsLike;
    }

    public void setCommentsLike(List<CommentEntity> commentsLike) {
        this.commentsLike = commentsLike;
    }
}
