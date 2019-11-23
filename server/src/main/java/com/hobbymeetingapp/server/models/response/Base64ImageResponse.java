package com.hobbymeetingapp.server.models.response;

public class Base64ImageResponse {
    private String pictureString;

    public Base64ImageResponse(String pictureString) {
        this.pictureString = pictureString;
    }

    public String getPictureString() {
        return pictureString;
    }
}
