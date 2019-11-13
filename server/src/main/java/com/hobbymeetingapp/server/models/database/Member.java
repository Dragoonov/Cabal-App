package com.hobbymeetingapp.server.models.database;

import com.hobbymeetingapp.server.models.EntityDel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class Member extends EntityDel {
    //Będę potrzebował także pomocy z ustaleniem Constraint - Default value dla uproszczenia.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @NotNull
    @Email
    @Column(name = "Email")
    private String email;

    @Column(name = "Token")
    private String token;

    // String with size larger than 8000 chars will be created as varchar(max)
    @Column(name = "Avatar", length = 8192)
    private String avatar;

    @NotNull
    @Column(name = "SearchRadius")
    private String searchRadius;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSearchRadius() {
        return searchRadius;
    }

    public void setSearchRadius(String searchRadius) {
        this.searchRadius = searchRadius;
    }
}
