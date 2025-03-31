package com.hau.Enum;

public enum DeliveryStatus {
    PENDING("Đang chờ xử lý"),
    CONFIRMED("Đã tiếp nhận"),
    SHIPPING("Đang giao hàng"),
    DELIVERED("Giao hàng thành công"),
    CANCELLED("Đã hủy đơn hàng");

    private final String displayName;
    DeliveryStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
