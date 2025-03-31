package com.hau.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
public class BrandEntity extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name ="country")
    private String country;
    @Column(name = "banner_url")
    private String bannerUrl;
    @Column(name ="icon_url")
    private String iconUrl;
    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<ProductLineEntity> productLines = new ArrayList<>();

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

    public List<ProductLineEntity> getProductLines() {
        return productLines;
    }

    public void setProductLines(List<ProductLineEntity> productLines) {
        this.productLines = productLines;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
