package com.haui.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "voucher_config")
public class VoucherConfig extends BaseEntity {
    @Column(name ="prefix")
    private String prefix;
    @Column(name = "discount_default")
    private long discountDefault;

    public VoucherConfig() {
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
