package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "success_story_reply")
public class SuccessStoryReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ss_id")
    private Integer ssId;

    @Column(name = "user_id")
    private Integer userId;

    private String content;

    @Column(name = "reply_time")
    private Date replyTime;

    @Transient
    private UserBasic user;
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
     * @return ss_id
     */
    public Integer getSsId() {
        return ssId;
    }

    /**
     * @param ssId
     */
    public void setSsId(Integer ssId) {
        this.ssId = ssId;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }


    public UserBasic getUser() {
        return user;
    }

    public void setUser(UserBasic user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SuccessStoryReply{" +
                "id=" + id +
                ", ssId=" + ssId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", replyTime=" + replyTime +
                ", user=" + user +
                '}';
    }
}