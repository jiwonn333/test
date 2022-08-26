package com.roopre.simpleboard;

public class ChatMsgVO {
    private String userId;
    private String crtDt;
    private String content;

    public ChatMsgVO() {
    }

    public ChatMsgVO(String userId, String crtDt, String content) {
        this.userId = userId;
        this.crtDt = crtDt;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatMsgVO{" +
                "userId='" + userId + '\'' +
                ", crtDt='" + crtDt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
