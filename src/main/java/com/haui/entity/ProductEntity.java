package com.haui.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Column(name = "water_resistant")
    private double waterResistant;
    @Column(name = "watch_type")
    private String watchType;
    @Column(name = "glass_material")
    private String glassMaterial;
    @Column(name = "strap_material")
    private String strapMaterial;
    @Column(name = "face_size")
    private String faceSize;
    @Column(name = "thickness")
    private double thickness;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "gender")
    private String gender;
    @Column(name = "price")
    private long price;
    @Column(name = "name")
    private String name;
    @Column(name = "instock")
    private int instock;
    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;
    @Column(name = "description")
    private String description;
    @ManyToOne (fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_line_id")
    private ProductLineEntity productLine;
    @ManyToMany(mappedBy = "products")
    private List<BillEntity> bills;
    @OneToMany(mappedBy = "product")
    List<CommentEntity> ratings;

    // Thêm thuộc tính cho mối quan hệ 1-nhiều với CartItemEntity
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItems;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CommentEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<CommentEntity> ratings) {
        this.ratings = ratings;
    }

    public Integer getInstock() {
        return instock;
    }

    public void setInstock(Integer instock) {
        this.instock = instock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWaterResistant() {
        return waterResistant;
    }

    public void setWaterResistant(double waterResistant) {
        this.waterResistant = waterResistant;
    }

    public String getWatchType() {
        return watchType;
    }

    public void setWatchType(String watchType) {
        this.watchType = watchType;
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public String getStrapMaterial() {
        return strapMaterial;
    }

    public void setStrapMaterial(String strapMaterial) {
        this.strapMaterial = strapMaterial;
    }

    public String getFaceSize() {
        return faceSize;
    }

    public void setFaceSize(String faceSize) {
        this.faceSize = faceSize;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public ProductLineEntity getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLineEntity productLine) {
        this.productLine = productLine;
    }

    public List<BillEntity> getBills() {
        return bills;
    }

    public void setBills(List<BillEntity> bills) {
        this.bills = bills;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public boolean isActive() {
        return active;
    }


}

