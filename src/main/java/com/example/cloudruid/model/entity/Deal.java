package com.example.cloudruid.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "deals")
public class Deal extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;


    public String getName() {
        return name;
    }

    public Deal setName(String name) {
        this.name = name;
        return this;
    }
}
