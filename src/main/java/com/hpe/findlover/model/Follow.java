package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "follow_id")
    private Integer followId;

    @Column(name = "follow_time")
    private Date followTime;

    @Transient
    private UserBasic userBasic;

    public UserBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(UserBasic followUserBasic) {
        this.userBasic = followUserBasic;
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
     * @return follow_id
     */
    public Integer getFollowId() {
        return followId;
    }

    /**
     * @param followId
     */
    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    /**
     * @return follow_time
     */
    public Date getFollowTime() {
        return followTime;
    }

    /**
     * @param followTime
     */
    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", userId=" + userId +
                ", followId=" + followId +
                ", followTime=" + followTime +
                '}';
    }
}