package com.cabal.app.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cabal.app.AfterRegisterActivity;
import com.cabal.app.Client;
import com.cabal.app.Service;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.navigation_bar.UserActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cabal.app.models.EventModel;
import com.cabal.app.models.HobbyTypeModel;
import com.cabal.app.models.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonLoader {

    private static final String TAG = "JsonLoader";
    private static final String EVENTS_JSON_FILENAME = "events.json";
    private static final String HOBBIES_JSON_FILENAME = "hobbies.json";
    private static final String USERS_JSON_FILENAME = "users.json";
    static private Service service = Client.getClient().create(Service.class);

    public static List<EventModel> loadEvents(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, EVENTS_JSON_FILENAME));
            List<EventModel> eventModelList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                EventModel eventModel = gson.fromJson(array.getString(i), EventModel.class);
                eventModelList.add(eventModel);
            }
            return eventModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<HobbyTypeModel> loadHobbies(Context context, List<HobbyTypeModel> hobbyTypeModels) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Call<JsonArray> tokenCall = service.getInterestsData(User.getTokenId());
            tokenCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.code() == 200) {
                        try {
                            JSONArray array = new JSONArray(response.body());

                            for (int i = 0; i < array.length(); i++) {
                                HobbyTypeModel hobbyTypeModel = gson.fromJson(array.getString(i), HobbyTypeModel.class);
                                hobbyTypeModels.add(hobbyTypeModel);
                                loadHobbyTypeChildren(i, hobbyTypeModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return hobbyTypeModels;
    }

    private static void loadHobbyTypeChildren(int id, HobbyTypeModel model) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Call<JsonArray> tokenCall = service.getInterestsChildrenData(User.getTokenId(), id);
            tokenCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.code() == 200) {
                        try {
                            JSONArray array = new JSONArray(response.body());
                            HobbyModel[] hobbies = new HobbyModel[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                HobbyModel hobbyModel = gson.fromJson(array.getString(i), HobbyModel.class);
                                hobbies[i] = hobbyModel;
                            }
                            model.setHobbies(hobbies);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<UserModel> loadUsers(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, USERS_JSON_FILENAME));
            List<UserModel> userModels = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                UserModel userModel = gson.fromJson(array.getString(i), UserModel.class);
                userModels.add(userModel);
            }
            return userModels;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json;
        InputStream is;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG, "path " + jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
