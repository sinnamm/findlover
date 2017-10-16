package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pseudonym;

    @Column(name = "reg_time")
    private Date regTime;

    @Column(name = "essay_count")
    private Integer essayCount;

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
     * @return pseudonym
     */
    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * @param pseudonym
     */
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    /**
     * @return reg_time
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * @param regTime
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * @return essay_count
     */
    public Integer getEssayCount() {
        return essayCount;
    }

    /**
     * @param essayCount
     */
    public void setEssayCount(Integer essayCount) {
        this.essayCount = essayCount;
    }
}