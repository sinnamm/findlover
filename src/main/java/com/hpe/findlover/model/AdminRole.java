package com.hpe.findlover.model;

import javax.persistence.*;

@Table(name = "admin_role")
public class AdminRole {
    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "role_id")
    private Integer roleId;

    public AdminRole() {
    }

    public AdminRole(Integer adminId, Integer roleId) {
        this.adminId = adminId;
        this.roleId = roleId;
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
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}