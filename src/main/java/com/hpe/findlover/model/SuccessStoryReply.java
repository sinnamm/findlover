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

    @Column(name = "reply_id")
    private Date replyId;

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
        return userId;
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

    /**
     * @return reply_id
     */
    public Date getReplyId() {
        return replyId;
    }

    /**
     * @param replyId
     */
    public void setReplyId(Date replyId) {
        this.replyId = replyId;
    }
}