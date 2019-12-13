package com.cabal.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cabal.app.Utils.AfterRegisterUserData;
import com.cabal.app.Utils.BackendCommunicator;
import com.cabal.app.Utils.ImageManager;
import com.cabal.app.Utils.User;
import com.cabal.app.hobbies_edit_list.Hobbies;

import java.util.Objects;

public class AfterRegisterActivity extends AppCompatActivity {

    public static final int GET_FROM_GALLERY = 1;

    Button afterRegisterAccept;
    Button setAvatarButton;
    ImageView avatarImage;
    EditText nickname;

    private String avatarString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        if(savedInstanceState == null){
            Hobbies.initializeHobbies(this);
        }
        nickname = findViewById(R.id.nicknameAdd);
        User.instantiate();
/*        //TODO: DELETE THAT
        User.setCoordinates(new double[]{50,30});*/
        afterRegisterAccept = findViewById(R.id.afterRegisterAccept);
        afterRegisterAccept.setOnClickListener(view -> {
            AfterRegisterUserData data = new AfterRegisterUserData(
                    avatarString,
                    nickname.getText().toString(),
                    Hobbies.getCheckedIds());
            User.setAvatarImage(avatarString);
            User.setRadius(5);
            User.setNick(nickname.getText().toString());
           // BackendCommunicator.postAfterRegisterData(data,this);
        });
        avatarImage = findViewById(R.id.avatarImage);
        loadDefaultImage();
        avatarImage.setOnClickListener(view -> fireAvatarPick());
        setAvatarButton = findViewById(R.id.setAvatarButton);
        setAvatarButton.setOnClickListener(view -> fireAvatarPick());
        Objects.requireNonNull(getSupportActionBar()).hide();
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
