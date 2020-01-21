package com.cabal.app.navigation_bar;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cabal.app.ProfileFragment;
import com.cabal.app.R;
import com.cabal.app.navigation_bar.search_events.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class UserActivity extends AppCompatActivity {

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

    private void getUserHobbies() {
        progressBar.setVisibility(View.GONE);
        viewsContainer.setVisibility(View.VISIBLE);
    }

    private void getUserData() {

    }

    private void getUserAvatar(int id) {

    }
}