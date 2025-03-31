package com.hau.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "warranty")
public class WarrantyEntity extends BaseEntity{
    @Column(name ="content", columnDefinition = "LONGTEXT")
    private String content;
    @OneToOne(mappedBy = "warranty")
    private ProductLineEntity productLineEntity;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProductLineEntity getProductLineEntity() {
        return productLineEntity;
    }

    public void setProductLineEntity(ProductLineEntity productLineEntity) {
        this.productLineEntity = productLineEntity;
    }
}
