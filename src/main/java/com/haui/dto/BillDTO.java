package com.haui.dto;

import com.haui.Enum.DeliveryStatus;


import java.util.List;

public class BillDTO extends AbstractDTO<BillDTO>{

    private String username;
    private String gender;
    private String email;
    private String note;
    private String receiverName;
    private String receiverPhone;
    private String receiverGender;
    private String paymentMethod;
    private List<CartItemDTO> cartItemDTOS;
    private List<ProductBillSnapshotDTO> productBillSnapshotDTOS;
    private String street;
    private String city;
    private String district;
    private String ward;
    private String phone;
    private double subtotal;
    private double discount;
    private double total;
    private String voucherCode;
    private String displayName;
    private String status;
    private DeliveryStatus deliveryStatus;
    private String cancelReason;


    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public List<CartItemDTO> getCartItemDTOS() {
        return cartItemDTOS;
    }

    public void setCartItemDTOS(List<CartItemDTO> cartItemDTOS) {
        this.cartItemDTOS = cartItemDTOS;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverUsername) {
        this.receiverName = receiverUsername;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverGender() {
        return receiverGender;
    }

    public void setReceiverGender(String receiverGender) {
        this.receiverGender = receiverGender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ProductBillSnapshotDTO> getProductBillSnapshotDTOS() {
        return productBillSnapshotDTOS;
    }

    public void setProductBillSnapshotDTOS(List<ProductBillSnapshotDTO> productBillSnapshotDTOS) {
        this.productBillSnapshotDTOS = productBillSnapshotDTOS;
    }
}
