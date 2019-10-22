package com.hobbymeetingapp.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hobbymeetingapp.app.hobbies_edit_list.Hobbies;
import com.hobbymeetingapp.app.navigation_bar.UserActivity;

public class AfterRegisterActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 1;

    Button afterRegisterAccept;
    Button setAvatarButton;
    ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        Hobbies.initializeHobbies(this);
        afterRegisterAccept = findViewById(R.id.afterRegisterAccept);
        afterRegisterAccept.setOnClickListener(view -> startActivity(new Intent(AfterRegisterActivity.this, UserActivity.class)));
        avatarImage = findViewById(R.id.avatarImage);
        Uri fileUri = Uri.parse("android.resource://com.hobbymeetingapp.app/" + R.drawable.default_avatar);
        Glide.with(getApplicationContext()).load(fileUri).into(avatarImage);
        avatarImage.setOnClickListener(view -> fireAvatarPick());
        setAvatarButton = findViewById(R.id.setAvatarButton);
        setAvatarButton.setOnClickListener(view -> fireAvatarPick());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri selectedImage = data.getData();
            Glide.with(getApplicationContext()).load(selectedImage).into(avatarImage);
        }
    }

    private void fireAvatarPick(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }
}
