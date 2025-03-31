package com.hau.dto;

public class UserFaceBookDto {
    private long id;
    private String name;
    private String email;
    private Picture picture;
    private String userName;

    // Getters và setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    class Picture {
        private Data data;

        // Getter và setter cho Data

        class Data {
            private String url;
            private int height;
            private int width;
            private boolean is_silhouette;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
// Getters và setters
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getImgUrl() {
        return this.getPicture().getData().getUrl();
    }


}
