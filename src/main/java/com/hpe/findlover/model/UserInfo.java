package com.hpe.findlover.model;

import com.github.pagehelper.PageInfo;

/**
 * 用户封装信息的类
 * @author sinnamm
 * @Date Create in  2017/10/23.
 */

public class UserInfo {

    private String message;
    private PageInfo pageInfo;

    public UserInfo(String message, PageInfo pageInfo) {
        this.message = message;
        this.pageInfo = pageInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
