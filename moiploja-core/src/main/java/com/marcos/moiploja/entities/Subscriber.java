package com.marcos.moiploja.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ive_marruda on 07/04/17.
 */
@Entity
public class Subscriber {
    @Id
    private String id;

    public Subscriber() {
    }

    public Subscriber(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
