package com.hpe.findlover.model;

import javax.persistence.*;

@Table(name = "user_label")
public class UserLabel {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "label_id")
    private Integer labelId;

    public UserLabel() {
    }

    public UserLabel(Integer userId, Integer labelId) {
        this.userId = userId;
        this.labelId = labelId;
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
     * @return label_id
     */
    public Integer getLabelId() {
        return labelId;
    }

    /**
     * @param labelId
     */
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    @Override
    public String toString() {
        return "UserLabel{" +
                "userId=" + userId +
                ", labelId=" + labelId +
                '}';
    }
}