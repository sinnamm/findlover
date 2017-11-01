package com.hpe.findlover.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private String content;

    @Column(name = "pub_time")
    private Date pubTime;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Transient
    private boolean isLike;

    @Transient
    private List<MessageLike> likes;

    @Transient
    private List<MessageReply> replies;

    @Transient
    private UserBasic userBasic;

    public Message() {
    }

    public Message(Integer id, Integer likeCount) {
        this.id = id;
        this.likeCount = likeCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public UserBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
    }

    public List<MessageLike> getLikes() {
        return likes;
    }

    public void setLikes(List<MessageLike> likes) {
        this.likes = likes;
    }

    public List<MessageReply> getReplies() {
        return replies;
    }

    public void setReplies(List<MessageReply> replies) {
        this.replies = replies;
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
     * @return pub_time
     */
    public Date getPubTime() {
        return pubTime;
    }

    /**
     * @param pubTime
     */
    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    /**
     * @return like_count
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return reply_count
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * @param replyCount
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", pubTime=" + pubTime +
                ", likeCount=" + likeCount +
                ", replyCount=" + replyCount +
                ",reply:{"+replies+"}"+
                '}';
    }
}