package com.hau.dto;

public class UserGoogleDto {
    private String id;

    private String email;

    private String given_name;

    private String picture;

    private boolean verified_email;

    private String family_name;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public UserGoogleDto(String id, String email, String given_name, String picture, boolean verified_email,
                         String family_name) {
        super();
        this.id = id;
        this.email = email;
        this.given_name = given_name;
        this.picture = picture;
        this.verified_email = verified_email;
        this.family_name = family_name;
    }

    @Override
    public String toString() {
        return "UserGoogleDto [id=" + id + ", email=" + email + ", given_name=" + given_name + ", picture=" + picture
                + ", verified_email=" + verified_email + ", family_name=" + family_name + "]";
    }

}
