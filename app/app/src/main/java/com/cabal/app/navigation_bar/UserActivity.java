package com.cabal.app.navigation_bar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cabal.app.Client;
import com.cabal.app.Service;
import com.cabal.app.Utils.User;
import com.cabal.app.hobbies_edit_list.Hobbies;
import com.cabal.app.models.HobbyModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.cabal.app.R;
import com.cabal.app.navigation_bar.search_events.SearchFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserActivity extends AppCompatActivity {

    static private Service service = Client.getClient().create(Service.class);
    private static final String TAG = "UserActivity";
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.nav_my_events:
                        selectedFragment = new MyEventsFragment();
                        break;
                    case R.id.nav_search:
                        selectedFragment = new SearchFragment();
                        break;
                }

                assert selectedFragment != null;
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            };

    private ProgressBar progressBar;
    private FrameLayout viewsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        viewsContainer = findViewById(R.id.fragment_container);
        viewsContainer.setVisibility(View.INVISIBLE);
        //TODO: GET USER HOBBIES
        getUserHobbies();
        getUserData();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();
        }
        Objects.requireNonNull(getSupportActionBar()).hide();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Hobbies.initializeHobbies(this);
    }

    private void getUserHobbies() {
        progressBar.setVisibility(View.GONE);
        viewsContainer.setVisibility(View.VISIBLE);
    }

    private void getUserData() {
        Call<JsonObject> tokenCall = service.getMeData("Bearer " + User.getTokenId());
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                    User.setId(response.body().get("id").getAsInt());
                    User.setNick(response.body().get("name").getAsString());
                    User.setRadius(response.body().get("searchRadius").getAsInt());
                    User.setHobbies(new Gson().fromJson(response.body().getAsJsonArray("interests"), HobbyModel[].class));
                    Hobbies.tickLikedHobbies();
                    Log.d(TAG, "onResponse: Hobbiesy" + Arrays.toString(User.getHobbies()));
                    getUserAvatar(User.getId());
                }
                else {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getUserAvatar(int id) {
        Call<JsonObject> tokenCall = service.getMeAvatarData("Bearer " + User.getTokenId(),id);
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                    User.setAvatarImage(response.body().get("pictureString").getAsString());
                }
                else {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}