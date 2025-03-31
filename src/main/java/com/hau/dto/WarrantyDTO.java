package com.hau.dto;

public class WarrantyDTO extends AbstractDTO {
    private String content;
    private long productLineId;
    private String productLineName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(long productLineId) {
        this.productLineId = productLineId;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
    }
}
