package com.hpe.findlover.model;

import javax.persistence.*;

public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String meaning;

    public Label() {
    }

    public Label(String name) {
        this.id=id;
        this.name = name;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }
}