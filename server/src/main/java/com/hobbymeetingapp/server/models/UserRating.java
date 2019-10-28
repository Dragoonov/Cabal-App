package com.hobbymeetingapp.server.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class UserRating extends EntityDel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    //Użytkownik którego oceniamy
    @NotNull
    @Column(name = "UserId")
    private Integer userId;

    @NotNull
    @Column(name = "RatingStatusId")
    private Integer ratingStatusId;

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

    public Integer getRatingStatusId() {
        return ratingStatusId;
    }

    public void setRatingStatusId(Integer ratingStatusId) {
        this.ratingStatusId = ratingStatusId;
    }
}
