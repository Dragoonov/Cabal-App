package com.cabal.app.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.cabal.app.models.EventModel;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.HobbyTypeModel;
import com.cabal.app.models.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

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
