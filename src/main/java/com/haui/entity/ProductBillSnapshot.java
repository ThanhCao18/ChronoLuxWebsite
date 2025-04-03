package com.haui.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_bill_snapshot")
public class ProductBillSnapshot extends BaseEntity {
    @Column
    private String productName;
    @Column
    private Integer quantity;
    @Column
    private long price;
    @Column
    private String imgUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }

    public ProductBillSnapshot() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ProductBillSnapshot(String productName, Integer quantity, long price, String imgUrl) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
