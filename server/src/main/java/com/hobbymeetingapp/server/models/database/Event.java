package com.hobbymeetingapp.server.models.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event extends EntityDel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventId")
    private Integer id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CreatorId")
    private Member creator;

    //Podejrzewam, że lokalizacja z GoogleAPI będzie stringiem.
    @NotNull
    @Column(name = "LocationId")
    private String locationId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "InterestId")
    private Interest interest;

    @NotNull
    @Column(name = "Date")
    private Date date;

    @Column(name = "DurationMinutes")
    private Integer durationMinutes;

    @NotNull
    @Column(name = "IsFinished", columnDefinition = "bit default 0")
    private Boolean isFinished;

    //Nie not null - brak wartości oznacza nieskończoność użytkowników
    @Column(name = "MaxParticipantsQuantity")
    private Integer maxParticipantsQuantity;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "event")
    private Set<MemberEvent> memberEvents = new HashSet<MemberEvent>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
        this.creator = creator;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Boolean isFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Integer getMaxParticipantsQuantity() {
        return maxParticipantsQuantity;
    }

    public void setMaxParticipantsQuantity(Integer maxParticipantsQuantity) {
        this.maxParticipantsQuantity = maxParticipantsQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
