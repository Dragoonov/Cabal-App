package com.hobbymeetingapp.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Interest extends EntityDel {
    //Aktualnie przyjmuję wersję która nie ma podkategorii. Jeszcze to przegadamy.

    //Mimo tego, że uznajemy że kategorie są predefiniowane Encję dodaję,
    //gdybysmy chcieli dodawać je z poziomu aplikacji

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @Column(name = "Picture")
    private String picture;

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
}
