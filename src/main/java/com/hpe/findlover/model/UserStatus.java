package com.hpe.findlover.model;

import javax.persistence.*;

@Table(name = "user_status")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "love_history")
    private String loveHistory;

    @Column(name = "marry_time")
    private String marryTime;

    private Integer ldr;

    @Column(name = "parent_status")
    private String parentStatus;

    @Column(name = "bro_and_sis")
    private String broAndSis;

    @Column(name = "family_brief")
    private String familyBrief;

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
     * @return love_history
     */
    public String getLoveHistory() {
        return loveHistory;
    }

    /**
     * @param loveHistory
     */
    public void setLoveHistory(String loveHistory) {
        this.loveHistory = loveHistory;
    }

    /**
     * @return marry_time
     */
    public String getMarryTime() {
        return marryTime;
    }

    /**
     * @param marryTime
     */
    public void setMarryTime(String marryTime) {
        this.marryTime = marryTime;
    }

    /**
     * @return ldr
     */
    public Integer getLdr() {
        return ldr;
    }

    /**
     * @param ldr
     */
    public void setLdr(Integer ldr) {
        this.ldr = ldr;
    }

    /**
     * @return parent_status
     */
    public String getParentStatus() {
        return parentStatus;
    }

    /**
     * @param parentStatus
     */
    public void setParentStatus(String parentStatus) {
        this.parentStatus = parentStatus;
    }

    /**
     * @return bro_and_sis
     */
    public String getBroAndSis() {
        return broAndSis;
    }

    /**
     * @param broAndSis
     */
    public void setBroAndSis(String broAndSis) {
        this.broAndSis = broAndSis;
    }

    /**
     * @return family_brief
     */
    public String getFamilyBrief() {
        return familyBrief;
    }

    /**
     * @param familyBrief
     */
    public void setFamilyBrief(String familyBrief) {
        this.familyBrief = familyBrief;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", loveHistory='" + loveHistory + '\'' +
                ", marryTime='" + marryTime + '\'' +
                ", ldr=" + ldr +
                ", parentStatus='" + parentStatus + '\'' +
                ", broAndSis='" + broAndSis + '\'' +
                ", familyBrief='" + familyBrief + '\'' +
                '}';
    }
}