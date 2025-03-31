package com.hau.dto;

public class CartItemDTO extends AbstractDTO<CartItemDTO>{
    private Integer quantity;
    private String customerName;
    private String productName;
    private Integer productQuantity;
    private long productPrice;
    private long productId;
    private Long billId;
    private long userId;
    private String productImgUrl;
    private long subtotal;
    private long total;
    private long discount;
    private String productType;
    private String username;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CartItemDTO() {
    }

    public CartItemDTO(Integer quantity, String productName, long productPrice, long productId, String productImgUrl,String username,Integer productQuantity,String watchType) {
        this.quantity = quantity;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productImgUrl = productImgUrl;
        this.username = username;
        this.productQuantity = productQuantity;
        this.productType = watchType;
    }

    public CartItemDTO(String productName, String subtotal, String total, String quantity) {
        this.productName = productName;
        this.subtotal =Long.parseLong(subtotal)  ;
        this.total =Long.parseLong(total);
        this.quantity = Integer.parseInt(quantity);
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getTotal() {
        return String.valueOf(total);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getSubtotal() {
        return String.valueOf(subtotal) ;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getQuantity() {
        return this.quantity.toString();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return String.valueOf(productPrice);
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }
}
