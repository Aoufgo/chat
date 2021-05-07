package com.ai.chat.pojo;

public class Admin {
    private int adminId;
    private String adminName;
    private String adminPassword;
    private int status;
    private String adminCreateTime;
    private String adminLoginTime;
    private int roleId;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Admin(int adminId, String adminName, String adminPassword, int status, String adminCreateTime, String adminLoginTime, int roleId) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.status = status;
        this.adminCreateTime = adminCreateTime;
        this.adminLoginTime = adminLoginTime;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", status=" + status +
                ", adminCreateTime='" + adminCreateTime + '\'' +
                ", adminLoginTime='" + adminLoginTime + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    public Admin() {
    }
    //    public String getAdminId() {
//        return adminId;
//    }
//
//    public void setAdminId(String adminId) {
//        this.adminId = adminId;
//    }
//
//    public String getAdminName() {
//        return adminName;
//    }
//
//    public void setAdminName(String adminName) {
//        this.adminName = adminName;
//    }
//
//    public String getAdminPassword() {
//        return adminPassword;
//    }
//
//    public void setAdminPassword(String adminPassword) {
//        this.adminPassword = adminPassword;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public String getAdminCreateTime() {
//        return adminCreateTime;
//    }
//
//    public void setAdminCreateTime(String adminCreateTime) {
//        this.adminCreateTime = adminCreateTime;
//    }
//
//    public String getAdminLoginTime() {
//        return adminLoginTime;
//    }
//
//    public void setAdminLoginTime(String adminLoginTime) {
//        this.adminLoginTime = adminLoginTime;
//    }
//
//    public Admin(String adminId, String adminName, String adminPassword, int status, String adminCreateTime, String adminLoginTime) {
//        this.adminId = adminId;
//        this.adminName = adminName;
//        this.adminPassword = adminPassword;
//        this.status = status;
//        this.adminCreateTime = adminCreateTime;
//        this.adminLoginTime = adminLoginTime;
//    }
//
//    @Override
//    public String toString() {
//        return "Admin{" +
//                "adminId='" + adminId + '\'' +
//                ", adminName='" + adminName + '\'' +
//                ", adminPassword='" + adminPassword + '\'' +
//                ", status=" + status +
//                ", adminCreateTime='" + adminCreateTime + '\'' +
//                ", adminLoginTime='" + adminLoginTime + '\'' +
//                '}';
//    }
//
//    public Admin() {
//    }
}
