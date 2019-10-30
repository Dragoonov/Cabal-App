package com.cabal.app;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @GET("/health")
    Call<Health> getHealth();

    @POST("member/login")
    Call<JsonObject> postToken(@Body JsonObject jsonObject);
}
