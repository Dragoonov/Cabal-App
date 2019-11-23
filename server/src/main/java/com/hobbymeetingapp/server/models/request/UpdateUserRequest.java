package com.hobbymeetingapp.server.models.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateUserRequest {
    @NotNull
    private String avatar;
    @NotNull
    private String nickname;
    @NotNull
    private int searchRadius;
    @NotNull
    private List<Integer> interestIds;

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSearchRadius() {
        return searchRadius;
    }

    public List<Integer> getInterestIds() {
        return interestIds;
    }
}
