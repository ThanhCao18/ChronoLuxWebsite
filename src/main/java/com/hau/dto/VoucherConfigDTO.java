package com.hau.dto;

public class VoucherConfigDTO extends AbstractDTO<VoucherConfigDTO> {
    private String prefix;
    private long discountDefault;

    public VoucherConfigDTO() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public long getDiscountDefault() {
        return discountDefault;
    }

    public void setDiscountDefault(long discountDefault) {
        this.discountDefault = discountDefault;
    }
}
