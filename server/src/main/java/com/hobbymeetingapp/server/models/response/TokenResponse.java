package com.hobbymeetingapp.server.models.response;

public class TokenResponse extends BaseResponse
{
    private String token;

    public TokenResponse() {
        this("");
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
