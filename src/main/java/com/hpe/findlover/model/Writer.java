package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pseudonym;

    private String username;

    private String password;

    private Integer status;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", pseudonym='" + pseudonym + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", regTime=" + regTime +
                ", essayCount=" + essayCount +
                '}';
    }
}