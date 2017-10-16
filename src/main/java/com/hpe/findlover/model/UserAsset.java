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

    @Column(name = "star_dealline")
    private Date starDealline;

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
     * @return star_dealline
     */
    public Date getStarDealline() {
        return starDealline;
    }

    /**
     * @param starDealline
     */
    public void setStarDealline(Date starDealline) {
        this.starDealline = starDealline;
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
}