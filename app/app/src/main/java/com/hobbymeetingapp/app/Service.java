package com.hobbymeetingapp.app;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @GET("/health")
    Call<Health> getHealth();

    @POST("/api/v1/member/login")
    Call<JsonObject> postToken(@Body JsonObject jsonObject);
}
