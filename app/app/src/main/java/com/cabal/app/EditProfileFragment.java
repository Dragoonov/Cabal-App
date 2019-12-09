package com.cabal.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.cabal.app.Utils.ImageManager;
import com.cabal.app.Utils.User;
import com.cabal.app.hobbies_edit_list.Hobbies;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    private String avatarUriString;
    private static final int GET_FROM_GALLERY = 1;
    private TextView seekBarDistance;
    private ImageView avatarPhoto;
    private Button changeAvatarBtn;
    private Button save;
    private EditText nick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        SeekBar seekBar = view.findViewById(R.id.seekBarRadius);
        seekBar.setOnSeekBarChangeListener(seekBarRadiusChangeListener);
        avatarPhoto = view.findViewById(R.id.avatarPhoto);
        save = view.findViewById(R.id.btnSave);
        nick = view.findViewById(R.id.editNickname);
        nick.setText(User.getNick());
        save.setOnClickListener(v -> {
            User.setAvatarImage(avatarUriString);
            User.setNick(nick.getText().toString());
            User.setRadius(seekBar.getProgress());
            FragmentManager manager = Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager();
            manager.beginTransaction()
                    .remove(this)
                    .commit();
            manager.popBackStack();
        });
        avatarUriString = User.getAvatarUri();
        Glide.with(Objects.requireNonNull(getContext()))
                .load(ImageManager.convertStringToBitmap(User.getAvatarUri()))
                .into(avatarPhoto);
        avatarPhoto.setOnClickListener(v -> fireAvatarPick());
        changeAvatarBtn = view.findViewById(R.id.changeAvatarBtn);
        changeAvatarBtn.setOnClickListener(v -> fireAvatarPick());


        int progress = seekBar.getProgress();
        seekBarDistance = view.findViewById(R.id.distance);
        seekBarDistance.setText(Integer.toString(progress));

        return view;
    }

    private SeekBar.OnSeekBarChangeListener seekBarRadiusChangeListener =
            new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            seekBarDistance.setText(Integer.toString(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            avatarUriString = savedInstanceState.getString("avatarUriString");
            Glide.with(Objects.requireNonNull(getContext()))
                    .asBitmap()
                    .load(ImageManager.convertStringToBitmap(avatarUriString))
                    .into(avatarPhoto);
        }

        if(savedInstanceState == null) {
            Hobbies.initializeHobbies(Objects.requireNonNull(getActivity()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = Objects.requireNonNull(data).getData();
            loadSelectedImage(selectedImage);
        }
    }

    private void loadSelectedImage(Uri selectedImage) {
        Bitmap resizedImage = ImageManager.scaleImage(selectedImage,
                Objects.requireNonNull(getActivity()).getContentResolver(),
                getContext());
        avatarUriString = ImageManager.convertBitmapToString(resizedImage);
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(resizedImage)
                .into(avatarPhoto);
    }

    private void fireAvatarPick(){
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("avatarUriString",avatarUriString);
    }
}
