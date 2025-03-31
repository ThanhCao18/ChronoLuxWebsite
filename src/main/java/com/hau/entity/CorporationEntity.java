package com.hau.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="corporation")
public class CorporationEntity extends  BaseEntity {
    @Column(name ="address")
    private String address;
    @Column(name ="phone")
    private String phone;
    @Column(name ="email")
    private String email;
    @Column(name ="about", columnDefinition = "LONGTEXT")
    private String about;
    @Column(name ="img_url")
    private String imgUrl;

    public CorporationEntity() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String policy) {
        this.email = policy;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
