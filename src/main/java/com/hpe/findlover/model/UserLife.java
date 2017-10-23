package com.hpe.findlover.model;

import javax.persistence.*;

@Table(name = "user_life")
public class UserLife {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer smoke;

    private Integer drink;

    private Integer car;

    private String job;

    @Column(name = "job_time")
    private String jobTime;

    @Column(name = "`character`")
    private String character;

    @Column(name = "job_brief")
    private String jobBrief;

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
     * @return smoke
     */
    public Integer getSmoke() {
        return smoke;
    }

    /**
     * @param smoke
     */
    public void setSmoke(Integer smoke) {
        this.smoke = smoke;
    }

    /**
     * @return drink
     */
    public Integer getDrink() {
        return drink;
    }

    /**
     * @param drink
     */
    public void setDrink(Integer drink) {
        this.drink = drink;
    }

    /**
     * @return car
     */
    public Integer getCar() {
        return car;
    }

    /**
     * @param car
     */
    public void setCar(Integer car) {
        this.car = car;
    }

    /**
     * @return job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return job_time
     */
    public String getJobTime() {
        return jobTime;
    }

    /**
     * @param jobTime
     */
    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    /**
     * @return character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * @param character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * @return job_brief
     */
    public String getJobBrief() {
        return jobBrief;
    }

    /**
     * @param jobBrief
     */
    public void setJobBrief(String jobBrief) {
        this.jobBrief = jobBrief;
    }

    @Override
    public String toString() {
        return "UserLife{" +
                "id=" + id +
                ", smoke=" + smoke +
                ", drink=" + drink +
                ", car=" + car +
                ", job='" + job + '\'' +
                ", jobTime='" + jobTime + '\'' +
                ", character='" + character + '\'' +
                ", jobBrief='" + jobBrief + '\'' +
                '}';
    }
}