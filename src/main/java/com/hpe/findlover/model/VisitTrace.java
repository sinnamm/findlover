package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "visit_trace")
public class VisitTrace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "interviewee_id")
    private Integer intervieweeId;

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "status")
    private Integer status;

    @Transient
    private UserBasic userBasic;

    public VisitTrace() {
    }



    public VisitTrace(Integer id,Integer userId, Integer intervieweeId, Date visitTime,Integer status) {
        this.id = id;
        this.userId = userId;
        this.intervieweeId = intervieweeId;
        this.visitTime = visitTime;
        this.status = status;
    }

    public UserBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
     * @return interviewee_id
     */
    public Integer getIntervieweeId() {
        return intervieweeId;
    }

    /**
     * @param intervieweeId
     */
    public void setIntervieweeId(Integer intervieweeId) {
        this.intervieweeId = intervieweeId;
    }

    /**
     * @return visit_time
     */
    public Date getVisitTime() {
        return visitTime;
    }

    /**
     * @param visitTime
     */
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    @Override
    public String toString() {
        return "VisitTrace{" +
                "id=" + id +
                ", userId=" + userId +
                ", intervieweeId=" + intervieweeId +
                ", visitTime=" + visitTime +
                ", status=" + status +
                ", userBasic=" + userBasic +
                '}';
    }
}