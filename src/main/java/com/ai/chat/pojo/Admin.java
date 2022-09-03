package com.ai.chat.pojo;

public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminPassword;
    private Integer status;
    private String adminCreateTime;
    private String adminLoginTime;
    private Integer roleId;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", status=" + status +
                ", adminCreateTime='" + adminCreateTime + '\'' +
                ", adminLoginTime='" + adminLoginTime + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAdminCreateTime() {
        return adminCreateTime;
    }

    public void setAdminCreateTime(String adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }

    public String getAdminLoginTime() {
        return adminLoginTime;
    }

    public void setAdminLoginTime(String adminLoginTime) {
        this.adminLoginTime = adminLoginTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Admin(Integer adminId, String adminName, String adminPassword, Integer status, String adminCreateTime, String adminLoginTime, Integer roleId) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.status = status;
        this.adminCreateTime = adminCreateTime;
        this.adminLoginTime = adminLoginTime;
        this.roleId = roleId;
    }
}
