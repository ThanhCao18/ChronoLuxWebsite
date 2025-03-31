package com.hau.dto;

import javax.persistence.Column;

public class BrandDTO extends AbstractDTO{

    private String name;

    private String country;

    private String bannerUrl;

    private String iconUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "BrandDTO{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
