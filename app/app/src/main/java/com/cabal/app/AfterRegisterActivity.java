package com.cabal.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cabal.app.Utils.AfterRegisterUserData;
import com.cabal.app.Utils.ImageManager;
import com.cabal.app.Utils.User;
import com.cabal.app.hobbies_edit_list.Hobbies;
import com.cabal.app.navigation_bar.UserActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterRegisterActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 1;
    private final int INITIAL_RADIUS = 5;
    private static final String TAG = "AfterRegisterActivity";

    Button afterRegisterAccept;
    Button setAvatarButton;
    ImageView avatarImage;
    EditText nickname;

    private String avatarString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        if(savedInstanceState == null) {
            Hobbies.initializeHobbies(this);
        }
        nickname = findViewById(R.id.nicknameAdd);
        afterRegisterAccept = findViewById(R.id.afterRegisterAccept);
        afterRegisterAccept.setOnClickListener(view -> {
            AfterRegisterUserData data = new AfterRegisterUserData(
                    avatarString,
                    nickname.getText().toString(),
                    Hobbies.getCheckedIds(),
                    INITIAL_RADIUS);
            User.setAvatarImage(avatarString);
            User.setRadius(INITIAL_RADIUS);
            User.setNick(nickname.getText().toString());
            postAfterRegisterData(data);
        });
        avatarImage = findViewById(R.id.avatarImage);
        loadDefaultImage();
        avatarImage.setOnClickListener(view -> fireAvatarPick());
        setAvatarButton = findViewById(R.id.setAvatarButton);
        setAvatarButton.setOnClickListener(view -> fireAvatarPick());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void postAfterRegisterData(AfterRegisterUserData data) {
        String requestBodyString = new Gson().toJson(data);
        JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
        startActivity(new Intent(this, UserActivity.class));
        finish();
    }

    private void loadDefaultImage(){
        Uri fileUri = Uri.parse("android.resource://com.cabal.app/" + R.drawable.default_avatar);
        Bitmap defaultImage = ImageManager.scaleImage(fileUri,getContentResolver(), getApplicationContext());
        avatarString = ImageManager.convertBitmapToString(defaultImage);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(defaultImage)
                .into(avatarImage);
    }

    private void loadSelectedImage(Uri selectedImage) {
        Bitmap resizedImage = ImageManager.scaleImage(selectedImage,getContentResolver(), getApplicationContext());
        avatarString = ImageManager.convertBitmapToString(resizedImage);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(resizedImage)
                .into(avatarImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            assert data != null;
            loadSelectedImage(data.getData());
        }
    }

    private void fireAvatarPick(){
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        GET_FROM_GALLERY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("avatarString", avatarString);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        avatarString = savedInstanceState.getString("avatarString");
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(ImageManager.convertStringToBitmap(avatarString))
                .into(avatarImage);
        Hobbies.initializeHobbies(this);
    }
}
