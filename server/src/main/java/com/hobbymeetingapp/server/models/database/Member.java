package com.hobbymeetingapp.server.models.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member extends EntityDel {
    //Będę potrzebował także pomocy z ustaleniem Constraint - Default value dla uproszczenia.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberId")
    private Integer id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @NotNull
    @Email
    @JsonIgnore
    @Column(name = "Email")
    private String email;

    @JsonIgnore
    @Column(name = "Token")
    private String token;

    // String with size larger than 8000 chars will be created as varchar(max)
    @JsonIgnore
    @Column(name = "Avatar", length = 8192)
    private String avatar;

    @NotNull
    @JsonIgnore
    @Column(name = "SearchRadius")
    private int searchRadius;

    @OneToMany(mappedBy = "member")
    private Set<MemberEvent> memberEvents = new HashSet<MemberEvent>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "MemberInterest",
            joinColumns = @JoinColumn(name = "MemberId"),
            inverseJoinColumns = @JoinColumn(name = "InterestId")
    )
    public List<Interest> interests;

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

    public int getSearchRadius() {
        return searchRadius;
    }

    public void setSearchRadius(int searchRadius) {
        this.searchRadius = searchRadius;
    }
}
