package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "message_reply")
public class MessageReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "user_id")
    private Integer userId;

    private String content;

    @Column(name = "reply_time")
    private Date replyTime;

    @Transient
    private UserBasic userBasic;

    public MessageReply() {
    }

    public MessageReply(Integer messageId, Integer userId, String content, Date replyTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.content = content;
        this.replyTime = replyTime;
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
     * @return reply_time
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * @param replyTime
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}