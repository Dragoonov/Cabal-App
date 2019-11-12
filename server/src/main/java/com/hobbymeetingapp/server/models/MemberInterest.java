package com.hobbymeetingapp.server.models;

import com.hobbymeetingapp.server.models.database.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class MemberInterest extends EntityDel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "MemberId")
    private Member member;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "InterestId")
    private Interest interest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }
}
