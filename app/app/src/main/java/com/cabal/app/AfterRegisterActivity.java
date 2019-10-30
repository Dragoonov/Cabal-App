package com.cabal.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cabal.app.hobbies_edit_list.Hobbies;
import com.cabal.app.navigation_bar.UserActivity;

import java.util.Objects;

public class AfterRegisterActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 1;

    Button afterRegisterAccept;
    Button setAvatarButton;
    ImageView avatarImage;
    private String avatarUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        if(savedInstanceState == null){
            Hobbies.initializeHobbies(this);
        }
        afterRegisterAccept = findViewById(R.id.afterRegisterAccept);
        afterRegisterAccept.setOnClickListener(view -> startActivity(new Intent(AfterRegisterActivity.this, UserActivity.class)));
        avatarImage = findViewById(R.id.avatarImage);
        Uri fileUri = Uri.parse("android.resource://com.cabal.app/" + R.drawable.default_avatar);
        avatarUriString = fileUri.toString();
        Glide.with(getApplicationContext()).load(fileUri).into(avatarImage);
        avatarImage.setOnClickListener(view -> fireAvatarPick());
        setAvatarButton = findViewById(R.id.setAvatarButton);
        setAvatarButton.setOnClickListener(view -> fireAvatarPick());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = Objects.requireNonNull(data).getData();
            avatarUriString = Objects.requireNonNull(selectedImage).toString();
            Glide.with(getApplicationContext()).load(selectedImage).into(avatarImage);
        }
    }

    private void fireAvatarPick(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("avatarUriString",avatarUriString);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        avatarUriString = savedInstanceState.getString("avatarUriString");
        Glide.with(getApplicationContext()).load(Uri.decode(avatarUriString)).into(avatarImage);
        Hobbies.initializeHobbies(this);
    }
}
