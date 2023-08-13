package com.yp.common;

import java.io.Serializable;

/**
 * @author 杨鹏
 * @version 1.0
 */
public class Message implements Serializable {
    private static final long serialversionUID = 1L;
    private String content;//内容
    private String sender;//
    private String getter;
    private String sendTime;
    private String mesType;//消息类型
    private byte[] file;//二进制文件


    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
