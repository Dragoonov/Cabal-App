package com.hobbymeetingapp.app;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/health")
    Call<Health> getHealth();
}
