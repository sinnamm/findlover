package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_asset")
public class UserAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vip_deadline")
    private Date vipDeadline;

    @Column(name = "star_deadline")
    private Date starDeadline;

    private Integer asset;

    private Double cost;

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
     * @return vip_deadline
     */
    public Date getVipDeadline() {
        return vipDeadline;
    }

    /**
     * @param vipDeadline
     */
    public void setVipDeadline(Date vipDeadline) {
        this.vipDeadline = vipDeadline;
    }

    /**
     * @return star_deadline
     */
    public Date getStarDeadline() {
        return starDeadline;
    }

    /**
     * @param starDeadline
     */
    public void setStarDeadline(Date starDeadline) {
        this.starDeadline = starDeadline;
    }

    /**
     * @return asset
     */
    public Integer getAsset() {
        return asset;
    }

    /**
     * @param asset
     */
    public void setAsset(Integer asset) {
        this.asset = asset;
    }

    /**
     * @return cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * @param cost
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "UserAsset{" +
                "id=" + id +
                ", vipDeadline=" + vipDeadline +
                ", starDeadline=" + starDeadline +
                ", asset=" + asset +
                ", cost=" + cost +
                '}';
    }
}