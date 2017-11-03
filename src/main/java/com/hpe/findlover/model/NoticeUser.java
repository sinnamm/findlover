package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "notice_user")
public class NoticeUser {
    @Id
    @Column(name = "notice_id")
    private Integer noticeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "read_time")
    private Date readTime;

    public NoticeUser() {
    }

    public NoticeUser(Integer userId) {
        this.userId = userId;
    }

    public NoticeUser(Integer noticeId, Integer userId, Date readTime) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.readTime = readTime;
    }

    /**
     * @return notice_id
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * @param noticeId
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
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
     * @return read_time
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "NoticeUser{" +
                "noticeId=" + noticeId +
                ", userId=" + userId +
                ", readTime=" + readTime +
                '}';
    }
}