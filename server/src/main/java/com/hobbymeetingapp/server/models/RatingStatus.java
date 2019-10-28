package com.hobbymeetingapp.server.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//Klasa która zawiera statusy ratingu czyli to nasze Podobami się/Nie podoba mi się/Nie mam zdania
@Entity
public class RatingStatus {
    //Zakładamy, że to będzie predefiniowane, ale dodaję na wszelki wypadek

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    //Wartości -1, 0, 1
    @NotNull
    @Column(name = "Value")
    private Integer value;

    @NotNull
    @Column(name = "StatusName")
    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
