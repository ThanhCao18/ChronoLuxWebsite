package com.haui.dto;

public class ProductDTO extends AbstractDTO{

    private double waterResistant;

    private String watchType;

    private String glassMaterial;

    private int stock;

    private String strapMaterial;

    private String faceSize;

    private double thickness;

    private String imgUrl;

    private String gender;
    private long brandId;

    private String warrantyContent;

    private long price;
    private String productLineName;
    private String brandName;
    private String name;
    private String country;
    private long productLineId;
    private String description;
    private int commentCount;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(long productLineId) {
        this.productLineId = productLineId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
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

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public String getFaceSize() {
        return faceSize;
    }

    public void setFaceSize(String faceSize) {
        this.faceSize = faceSize;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarrantyContent() {
        return warrantyContent;
    }

    public void setWarrantyContent(String warrantyContent) {
        this.warrantyContent = warrantyContent;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
