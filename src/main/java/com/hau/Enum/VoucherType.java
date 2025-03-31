package com.hau.Enum;

public enum VoucherType {
    PUBLIC("Dùng chung"),
    PRIVATE("Cá nhân");

    private final String displayName;

    VoucherType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
