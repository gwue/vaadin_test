package org.vaadin.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.vaadin.example.PileEntry.PileState;

@Entity
@Table(name = "pileEntries")
public class PileEntryData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private PileState state;
    private String name;

    public PileEntryData(String name) {
        this(name, PileState.NEW);
    }

    public PileEntryData(String name, PileState state) {
        this.name = name;
        this.state = state;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setState(PileState state) {
        this.state = state;
    }

    public PileState getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}