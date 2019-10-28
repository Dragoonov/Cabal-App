package com.hobbymeetingapp.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserInterest extends EntityDel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "UserId")
    private Integer userId;

    @NotNull
    @Column(name = "InterestId")
    private Integer interestId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }
}
