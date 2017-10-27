package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "message_like")
public class MessageLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "like_time")
    private Date likeTime;

    @Transient
    private UserBasic userBasic;

    public MessageLike() {
    }

    public MessageLike(Integer messageId, Integer userId, Date likeTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.likeTime = likeTime;
    }

    public UserBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
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
     * @return message_id
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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