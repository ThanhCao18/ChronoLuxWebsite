package com.hau.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_line")
public class ProductLineEntity extends BaseEntity{
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name= "icon_url")
    private String iconUrl;
    @Column(name = "banner_url")
    private String banner;
    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="warranty_id")
    private WarrantyEntity warranty;

    @OneToMany(mappedBy = "productLine", cascade = CascadeType.REMOVE)
    private List<ProductEntity> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public WarrantyEntity getWarranty() {
        return warranty;
    }

    public void setWarranty(WarrantyEntity warranty) {
        this.warranty = warranty;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
