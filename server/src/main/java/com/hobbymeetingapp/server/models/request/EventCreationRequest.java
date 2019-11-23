package com.hobbymeetingapp.server.models.request;

import javax.validation.constraints.NotNull;

public class EventCreationRequest {
    @NotNull
    private String name;
    @NotNull
    private int interestId;

    public String getName() {
        return name;
    }

    public int getInterestId() {
        return interestId;
    }
}
