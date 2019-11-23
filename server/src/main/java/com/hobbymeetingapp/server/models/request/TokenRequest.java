package com.hobbymeetingapp.server.models.request;

import javax.validation.constraints.NotNull;

public class TokenRequest
{
    @NotNull
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
