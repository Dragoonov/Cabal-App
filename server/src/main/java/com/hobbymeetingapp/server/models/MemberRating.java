package com.hobbymeetingapp.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class MemberRating extends EntityDel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    //Użytkownik którego oceniamy
    @NotNull
    @Column(name = "MemberId")
    private Integer memberId;

    @NotNull
    @Column(name = "RatingStatusId")
    private Integer ratingStatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRatingStatusId() {
        return ratingStatusId;
    }

    public void setRatingStatusId(Integer ratingStatusId) {
        this.ratingStatusId = ratingStatusId;
    }
}
