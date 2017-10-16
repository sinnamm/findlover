package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "writer_essay_like")
public class WriterEssayLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "essay_id")
    private Integer essayId;

    @Column(name = "like_time")
    private Date likeTime;

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
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return essay_id
     */
    public Integer getEssayId() {
        return essayId;
    }

    /**
     * @param essayId
     */
    public void setEssayId(Integer essayId) {
        this.essayId = essayId;
    }

    /**
     * @return like_time
     */
    public Date getLikeTime() {
        return likeTime;
    }

    /**
     * @param likeTime
     */
    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}