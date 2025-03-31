package com.hau.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO extends AbstractDTO{
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private int status;
    private String imgUrl;
    private String resetPasswordToken;
    private List<String> roleCode = new ArrayList<>();
    private String firstName;
    private String surName;
    private Long voucherId;




    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public List<String> getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(List<String> roleCode) {
        this.roleCode = roleCode;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
    public String getFirstName(){
        if(this.fullName == null){
            return this.firstName;
        }
        String[] parts = this.fullName.split(" ");
        String firstName = parts[parts.length -1];
        if(firstName == null){
            firstName = "";
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName(){
        if(this.fullName == null){
            return this.surName;
        }
        int lastSpaceIndex = this.fullName.lastIndexOf(" ");
        String surName = "";
        if(lastSpaceIndex == -1){
            surName = "";
        }
        else{
            surName = this.fullName.substring(0,lastSpaceIndex);
        }
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }


}
