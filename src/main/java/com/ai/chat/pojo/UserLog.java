package com.ai.chat.pojo;

/**
 * @author aoufgo
 * @date 2021/5/10 下午10:22
 */
public class UserLog {
    private int logId;
    private String userId;
    private String operation;
    private String createTime;

    @Override
    public String toString() {
        return "UserLog{" +
                "logId=" + logId +
                ", userId='" + userId + '\'' +
                ", operation='" + operation + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public UserLog(int logId, String userId, String operation, String createTime) {
        this.logId = logId;
        this.userId = userId;
        this.operation = operation;
        this.createTime = createTime;
    }
}
