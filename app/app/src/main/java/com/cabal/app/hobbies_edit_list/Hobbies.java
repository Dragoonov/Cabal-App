package com.cabal.app.hobbies_edit_list;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cabal.app.Client;
import com.cabal.app.R;
import com.cabal.app.Service;
import com.cabal.app.Utils.User;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.HobbyTypeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Hobbies {

    private static Map<HobbyModel, Boolean> switchesState = new LinkedHashMap<>();
    private static List<HobbyTypeModel> hobbyTypeModels;
    static private Service service = Client.getClient().create(Service.class);
    private static final String TAG = "Hobbies";

    public static void initializeHobbies(Activity activity) {
        LinearLayout linearView = activity.findViewById(R.id.hobbiesContainer);
        if (linearView == null) {
            return;
        }
        if (hobbyTypeModels == null) {
            hobbyTypeModels = new ArrayList<>();
            loadHobbies(activity, hobbyTypeModels, linearView);
        }
        else {
            for (HobbyTypeModel model : hobbyTypeModels) {
                View hobbyTypeModel = initializeHobbyTypeView(model, linearView);
                linearView.addView(hobbyTypeModel);
            }
        }

    }

    public static List<HobbyTypeModel> loadHobbies(Context context, List<HobbyTypeModel> hobbyTypeModels, LinearLayout linearView) {

        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            //TODO Delete below
            User.setTokenId("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkcmFnb0BkcmEucGwiLCJpYXQiOjE1NzYzMTA0NTcsImV4cCI6MTYwNzg0NjQ1N30.Fhpk2iw9rFc1QTWX6QDRClAbumDpwPlMhN-R9M-YB7wQsfHp5Ef-ve1DR1quGcippRHEJKXti-71y7tESx-Wbg");
            Call<JsonArray> tokenCall = service.getInterestsData("Bearer " + User.getTokenId());
            tokenCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.code() == 200) {
                        try {
                            Log.d(TAG, "onResponse loadHobbies: " + response.code() + ", " + response.message());
                            JSONArray array = new JSONArray(response.body().toString());

                            for (int i = 0; i < array.length(); i++) {
                                HobbyTypeModel hobbyTypeModel = gson.fromJson(array.getString(i), HobbyTypeModel.class);
                                hobbyTypeModels.add(hobbyTypeModel);
                            }

                            for (HobbyTypeModel model : hobbyTypeModels) {
                                loadHobbyTypeChildren(model.getId(), model, linearView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "onResponse loadHobbies: " + response.code() + ", " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onFailure loadHobbies: " + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return hobbyTypeModels;
    }

    private static void loadHobbyTypeChildren(int id, HobbyTypeModel model, LinearLayout linearView) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Call<JsonArray> tokenCall = service.getInterestsChildrenData("Bearer " + User.getTokenId(), id);
            tokenCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse loadChildren: " + response.code() + ", " + response.message());
                        try {
                            JSONArray array = new JSONArray(response.body().toString());
                            HobbyModel[] hobbies = new HobbyModel[array.length()];
                            for (int i = 0; i < array.length(); i++) {
                                HobbyModel hobbyModel = gson.fromJson(array.getString(i), HobbyModel.class);
                                hobbies[i] = hobbyModel;
                            }
                            model.setHobbies(hobbies);
                            View hobbyTypeModel = initializeHobbyTypeView(model, linearView);
                            linearView.addView(hobbyTypeModel);
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

    public static int[] getCheckedIds() {
        return switchesState.keySet().stream()
                .filter(s -> switchesState.get(s))
                .map(HobbyModel::getId)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static View initializeHobbyTypeView(HobbyTypeModel model, LinearLayout parent) {
        View hobbyTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_type_item, parent, false);
        ((TextView) hobbyTypeView.findViewById(R.id.hobbyTypeTitle)).setText(model.getName());
        LinearLayout hobbies = hobbyTypeView.findViewById(R.id.subItem);
        for (HobbyModel hobbyModel : model.getHobbies()) {
            View hobby = initializeHobbyView(hobbyModel, hobbies);
            hobbies.addView(hobby);
        }
        setHobbiesListVisibility(hobbies, model.isExpanded());
        setArrowsVisibility(hobbyTypeView, model.isExpanded());
        hobbyTypeView.setOnClickListener(view1 -> {
            boolean expanded = model.isExpanded();
            model.setExpanded(!expanded);
            setHobbiesListVisibility(hobbies, model.isExpanded());
            setArrowsVisibility(view1, model.isExpanded());
        });
        return hobbyTypeView;
    }

    private static View initializeHobbyView(HobbyModel model, LinearLayout parent) {
        View hobbyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_item, parent, false);
        ((TextView) hobbyView.findViewById(R.id.nameHobby)).setText(model.getName());
        Glide.with(hobbyView.getContext()).load(model.getDescription()).into((ImageView) hobbyView.findViewById(R.id.photoHobby));
        Switch switchToggle = hobbyView.findViewById(R.id.switchHobby);
        hobbyView.setOnClickListener(hobby -> {
            switchToggle.setChecked(!switchToggle.isChecked());
            switchesState.put(model, switchToggle.isChecked());
        });
        Boolean switchState = switchesState.get(model);
        switchToggle.setChecked(switchState == null ? false : switchState);
        if (switchState == null) {
            switchesState.put(model, false);
        }
        return hobbyView;
    }

    private static void setHobbiesListVisibility(LinearLayout list, boolean visible) {
        list.setVisibility(visible ? VISIBLE : GONE);
    }

    private static void setArrowsVisibility(View view, boolean visible) {
        ImageView arrowDown = view.findViewById(R.id.iconArrowDown);
        ImageView arrowUp = view.findViewById(R.id.iconArrowUp);
        arrowUp.setVisibility(visible ? VISIBLE : GONE);
        arrowDown.setVisibility(visible ? GONE : VISIBLE);
    }
}
