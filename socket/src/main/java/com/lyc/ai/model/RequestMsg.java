package com.lyc.ai.model;

public class RequestMsg {
    private String body;

    public RequestMsg() {
    }

    public RequestMsg(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
