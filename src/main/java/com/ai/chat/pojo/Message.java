package com.ai.chat.pojo;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -8985545025228238754L;

    private String fromId;
    private String toId;
    private String msg;
    private String sendTime;
    private int isRead;

    public Message(String fromId, String toId, String msg, String sendTime, int isRead) {
        this.fromId = fromId;
        this.toId = toId;
        this.msg = msg;
        this.sendTime = sendTime;
        this.isRead = isRead;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", msg='" + msg + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
