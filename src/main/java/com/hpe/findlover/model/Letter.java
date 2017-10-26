package com.hpe.findlover.model;

import java.util.Date;
import javax.persistence.*;

public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "send_id")
    private Integer sendId;

    @Column(name = "recieve_id")
    private Integer recieveId;

    private String content;

    @Column(name = "send_time")
    private Date sendTime;

    private Integer status;

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
     * @return send_id
     */
    public Integer getSendId() {
        return sendId;
    }

    /**
     * @param sendId
     */
    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    /**
     * @return recieve_id
     */
    public Integer getRecieveId() {
        return recieveId;
    }

    /**
     * @param recieveId
     */
    public void setRecieveId(Integer recieveId) {
        this.recieveId = recieveId;
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
     * @return send_time
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Letter{" +
                "id=" + id +
                ", sendId=" + sendId +
                ", recieveId=" + recieveId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", status=" + status +
                '}';
    }
}