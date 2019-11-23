package com.hobbymeetingapp.server.models.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Interest extends EntityDel {
    //Mimo tego, że uznajemy że kategorie są predefiniowane Encję dodaję,
    //gdybysmy chcieli dodawać je z poziomu aplikacji

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @JsonIgnore
    @Column(name = "Picture")
    private String picture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Parent")
    private Interest parent;

    @JsonIgnore
    @ManyToMany(mappedBy = "interests")
    public List<Member> members;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Interest getParent() {
        return parent;
    }

    public void setParent(Interest parent) {
        this.parent = parent;
    }
}
