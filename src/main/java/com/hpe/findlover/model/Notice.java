package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "admin_id")
    private Integer adminId;

    private String title;

    private String content;

    @Column(name = "pub_time")
    private Date pubTime;

    /**
     * 0:所有用户，1:vip，2:星级用户，Id:用户id
     */
    @Column(name = "pub_obj")
    private Integer pubObj;


    public Notice() {
    }

    public Notice(Integer adminId, String title, String content, Date pubTime, Integer pubObj) {
        this.adminId = adminId;
        this.title = title;
        this.content = content;
        this.pubTime = pubTime;
        this.pubObj = pubObj;
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
     * 获取0:所有用户，1:vip，2:星级用户，Id:用户id
     *
     * @return pub_obj - 0:所有用户，1:vip，2:星级用户，Id:用户id
     */
    public Integer getPubObj() {
        return pubObj;
    }

    /**
     * 设置0:所有用户，1:vip，2:星级用户，Id:用户id
     *
     * @param pubObj 0:所有用户，1:vip，2:星级用户，Id:用户id
     */
    public void setPubObj(Integer pubObj) {
        this.pubObj = pubObj;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pubTime=" + pubTime +
                ", pubObj=" + pubObj +
                '}';
    }
}