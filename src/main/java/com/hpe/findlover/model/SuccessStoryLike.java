package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "success_story_like")
public class SuccessStoryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "success_story_id")
    private Integer successStoryId;

    @Column(name = "user_id")
    private Integer userId;

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
     * @return success_story_id
     */
    public Integer getSuccessStoryId() {
        return successStoryId;
    }

    /**
     * @param successStoryId
     */
    public void setSuccessStoryId(Integer successStoryId) {
        this.successStoryId = successStoryId;
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