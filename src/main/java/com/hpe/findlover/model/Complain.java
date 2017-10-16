package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "com_obj")
    private Integer comObj;

    private String reason;

    private String content;

    @Column(name = "com_time")
    private Date comTime;

    /**
     * 0：待处理、1：忽略、2：警告、3：封号
     */
    private Integer status;

    @Column(name = "admin_id")
    private Integer adminId;

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
     * @return com_obj
     */
    public Integer getComObj() {
        return comObj;
    }

    /**
     * @param comObj
     */
    public void setComObj(Integer comObj) {
        this.comObj = comObj;
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
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
     * @return com_time
     */
    public Date getComTime() {
        return comTime;
    }

    /**
     * @param comTime
     */
    public void setComTime(Date comTime) {
        this.comTime = comTime;
    }

    /**
     * 获取0：待处理、1：忽略、2：警告、3：封号
     *
     * @return status - 0：待处理、1：忽略、2：警告、3：封号
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0：待处理、1：忽略、2：警告、3：封号
     *
     * @param status 0：待处理、1：忽略、2：警告、3：封号
     */
    public void setStatus(Integer status) {
        this.status = status;
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
}