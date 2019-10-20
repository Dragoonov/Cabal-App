package com.hobbymeetingapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hobbymeetingapp.app.hobbies_edit_list.Hobbies;
import com.hobbymeetingapp.app.navigation_bar.UserActivity;


public class AfterRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        Hobbies.initializeHobbies(this);
        findViewById(R.id.button2).setOnClickListener(view -> startActivity(new Intent(AfterRegisterActivity.this, UserActivity.class)));
    }
}
