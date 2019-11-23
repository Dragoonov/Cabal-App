package com.hobbymeetingapp.server.models.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public class EntityDel {
    //Pola które się powtarzają w praktycznie każdej encji. Dodałem i zrobiłem,
    //że każda encja to dziedziczy, ale nie mam pojęcia czy Spring tak działa

    @NotNull
    @JsonIgnore
    @Column(name = "IsDeleted", columnDefinition = "bit default 0")
    private Boolean deleted;

    @JsonIgnore
    @Column(name = "DeletedBy")
    private Integer deletedBy;

    @JsonIgnore
    @Column(name = "DeletedAt")
    private Date deletedAt;

    @JsonIgnore
    @Column(name = "CreatedBy")
    private Integer createdBy;

    @JsonIgnore
    @Column(name = "CreatedAt")
    private Date createdAt;

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
