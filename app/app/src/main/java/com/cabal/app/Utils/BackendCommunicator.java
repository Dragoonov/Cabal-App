package com.cabal.app.Utils;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cabal.app.AfterRegisterActivity;
import com.cabal.app.Client;
import com.cabal.app.Configuration;
import com.cabal.app.Service;
import com.cabal.app.navigation_bar.UserActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackendCommunicator {

    static private Service service = Client.getClient().create(Service.class);
    private static final String TAG = "BackendCommunicator";

    public static void postUserDataToBackend(String idToken, AppCompatActivity activity) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("token", idToken);
        Call<JsonObject> tokenCall = service.postToken(requestBody);
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(activity, UserActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else if (response.code() == 201) {
                    Intent intent = new Intent(activity, AfterRegisterActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(), "Porażka!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void postAfterRegisterData(AfterRegisterUserData userData, AppCompatActivity activity) {
        Log.d(TAG, "postAfterRegisterData: DATA: " + userData);
        String requestBodyString = new Gson().toJson(userData);
        JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
        Log.d(TAG, "postAfterRegisterData: " + requestBody);
        Call<JsonObject> tokenCall = service.postRegisterData(Configuration.AUTH_TOKEN_PROD,requestBody);
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse: " + response.code() + " " + response.message());
                activity.startActivity(new Intent(activity, UserActivity.class));
                activity.finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
