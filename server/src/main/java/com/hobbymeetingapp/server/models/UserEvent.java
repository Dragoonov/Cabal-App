package com.hobbymeetingapp.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserEvent extends EntityDel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "UserId")
    private Integer userId;

    @NotNull
    @Column(name = "EventId")
    private Integer eventId;

    //Default value = false
    @NotNull
    @Column(name = "IsAccepted")
    private Boolean isAccepted;

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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
