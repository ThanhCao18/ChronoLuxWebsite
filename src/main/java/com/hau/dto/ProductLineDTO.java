package com.hau.dto;

public class ProductLineDTO extends AbstractDTO<ProductLineDTO>{
    private String name;
    private String brandName;
    private long brandId;
    private long warrantyId;
    private String bannerUrl;
    private String iconUrl;

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public long getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(long warrantyId) {
        this.warrantyId = warrantyId;
    }
}
