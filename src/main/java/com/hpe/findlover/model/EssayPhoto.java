package com.hpe.findlover.model;

import javax.persistence.*;
import java.util.Date;

public class EssayPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "essay_id")
    private Integer essayId;

    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEssayId() {
        return essayId;
    }

    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "EssayPhoto{" +
                "id=" + id +
                ", essayId=" + essayId +
                ", photo='" + photo + '\'' +
                '}';
    }
}