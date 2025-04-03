package com.haui.entity;

import com.haui.Enum.VoucherType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "voucher")
public class VoucherEntity extends BaseEntity {
    @Column(name = "code")
    private String code;
    @Column(name = "discount")
    private long discount;
    @Column(name = "begin_day")
    private Date beginDay;
    @Column(name = "end_day")
    private Date endDay;
    @OneToMany(mappedBy = "voucher")
    private List<BillEntity> bills = new ArrayList<>();
    @OneToMany(mappedBy = "voucher")
    private List<UserEntity> users = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VoucherType type;
    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public List<BillEntity> getBills() {
        return bills;
    }

    public void setBills(List<BillEntity> bills) {
        this.bills = bills;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public Date getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(Date beginDay) {
        this.beginDay = beginDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

}
