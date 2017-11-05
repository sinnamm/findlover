package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "success_story")
public class SuccessStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "left_user")
    private Integer leftUser;

    @Column(name = "right_user")
    private Integer rightUser;

    private String title;

    private String brief;

    private String photo;

    @Column(name = "success_time")
    private Date successTime;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "reply_count")
    private Integer replyCount;

    /**
     * 0：下架，1：审核通过，2：待右手审核，3：待管理员审核
     */
    private Integer status;

    @Column(name = "admin_id")
    private Integer adminId;

    private String content;

    @Transient
    private UserBasic userLeft;

    @Transient
    private UserBasic userRight;

    public SuccessStory() {
    }

    public SuccessStory(Integer leftUser,Integer rightUser){this.leftUser = leftUser;
    this.rightUser = rightUser;}


    public SuccessStory(Integer status) {
        this.status = status;
    }

    public UserBasic getUserLeft() {
        return userLeft;
    }

    public void setUserLeft(UserBasic userLeft) {
        this.userLeft = userLeft;
    }

    public UserBasic getUserRight() {
        return userRight;
    }

    public void setUserRight(UserBasic userRight) {
        this.userRight = userRight;
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
     * @return left_user
     */
    public Integer getLeftUser() {
        return leftUser;
    }

    /**
     * @param leftUser
     */
    public void setLeftUser(Integer leftUser) {
        this.leftUser = leftUser;
    }

    /**
     * @return right_user
     */
    public Integer getRightUser() {
        return rightUser;
    }

    /**
     * @param rightUser
     */
    public void setRightUser(Integer rightUser) {
        this.rightUser = rightUser;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return success_time
     */
    public Date getSuccessTime() {
        return successTime;
    }

    /**
     * @param successTime
     */
    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
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

    /**
     * 获取0：下架，1：审核通过，2：待右手审核，3：待管理员审核
     *
     * @return status - 0：下架，1：审核通过，2：待右手审核，3：待管理员审核
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0：下架，1：审核通过，2：待右手审核，3：待管理员审核
     *
     * @param status 0：下架，1：审核通过，2：待右手审核，3：待管理员审核
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return admin_id
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * @param adminId
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    @Override
    public String toString() {
        return "SuccessStory{" +
                "id=" + id +
                ", leftUser=" + leftUser +
                ", rightUser=" + rightUser +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", photo='" + photo + '\'' +
                ", successTime=" + successTime +
                ", likeCount=" + likeCount +
                ", replyCount=" + replyCount +
                ", status=" + status +
                ", adminId=" + adminId +
                ", content='" + content + '\'' +
                ", user=" + userLeft +
                '}';
    }
}