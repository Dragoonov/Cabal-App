package com.cabal.app;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
    @GET("health")
    Call<Health> getHealth();

    @POST("member/login")
    Call<JsonObject> postLoginData(@Body JsonObject jsonObject);

    @POST("member/register")
    Call<JsonObject> postRegisterData(@Body JsonObject jsonObject);

    @POST("member/update")
    Call<JsonObject> postAfterRegisterData(@Header("Authorization") String auth, @Body JsonObject jsonObject);

    @GET("interests")
    Call<JsonArray> getInterestsData(@Header("Authorization") String auth);

    @GET("interests/{interestId}/children")
    Call<JsonObject> getInterestsChildrenData(@Header("Authorization") String auth, @Path("interestId") int id);
}
