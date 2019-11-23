package com.hobbymeetingapp.server.models.request;

import javax.validation.constraints.NotNull;

public class JoinEventRequest {
    @NotNull
    private boolean isAccepted;

    public boolean getIsAccepted() {
        return isAccepted;
    }
}