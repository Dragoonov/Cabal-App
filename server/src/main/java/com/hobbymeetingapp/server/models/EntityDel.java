package com.hobbymeetingapp.server.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public class EntityDel {
    //Pola które się powtarzają w praktycznie każdej encji. Dodałem i zrobiłem,
    //że każda encja to dziedziczy, ale nie mam pojęcia czy Spring tak działa

    @NotNull
    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @Column(name = "DeletedBy")
    private Integer deletedBy;

    @Column(name = "DeletedAt")
    private Date deletedAt;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private Date createdAt;

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
