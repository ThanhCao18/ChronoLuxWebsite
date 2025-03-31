package com.hau.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tu tang
    private long id;
    @Column(name ="created_by")
    @CreatedBy
    private String createBy;
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
    @Column(name = "created_date")
    @CreatedDate
    private Date createDate;
    @Column(name =" modified_date")
    @LastModifiedDate
    private Date modifiedDate;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCreateBy() {
        return createBy;
    }
    public String getModifiedBy() {
        return modifiedBy;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }
}
