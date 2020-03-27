package org.vaadin.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="testTable")
public class TestData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; 

    private String name;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
    return this.name;
    }
}