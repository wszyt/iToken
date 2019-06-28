package com.zyt.itoken.common.constants;

public enum HttpStatusContants {
    BAD_GATEWAY(502, "从上游服务器接收到无效的响应");

    private int status;
    private String content;

    private HttpStatusContants(int status, String content) {
        this.status = status;
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
