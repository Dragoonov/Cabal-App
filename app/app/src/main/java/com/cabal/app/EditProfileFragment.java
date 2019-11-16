package com.cabal.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cabal.app.hobbies_edit_list.Hobbies;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    private String avatarUriString;
    public static final int GET_FROM_GALLERY = 1;
    TextView seekBarDistance;
    ImageView avatarPhoto;
    Button changeAvatarBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        avatarPhoto = view.findViewById(R.id.avatarPhoto);
        Uri fileUri = Uri.parse("android.resource://com.cabal.app/" + R.drawable.default_avatar);
        avatarUriString = fileUri.toString();
        Glide.with(getContext()).load(fileUri).into(avatarPhoto);
        avatarPhoto.setOnClickListener(v -> fireAvatarPick());
        changeAvatarBtn = view.findViewById(R.id.changeAvatarBtn);
        changeAvatarBtn.setOnClickListener(v -> fireAvatarPick());

        SeekBar seekBar = view.findViewById(R.id.seekBarRadius);
        seekBar.setOnSeekBarChangeListener(seekBarRadiusChangeListener);

        int progress = seekBar.getProgress();
        seekBarDistance = view.findViewById(R.id.distance);
        seekBarDistance.setText(String.valueOf("" + progress));

        return view;
    }

    private SeekBar.OnSeekBarChangeListener seekBarRadiusChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            seekBarDistance.setText(String.valueOf("" + progress));
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

        //Hobbies.initializeHobbies(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            avatarUriString = savedInstanceState.getString("avatarUriString");
            Glide.with(getContext()).load(Uri.decode(avatarUriString)).into(avatarPhoto);
        }

        if(savedInstanceState == null) {
            Hobbies.initializeHobbies(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = Objects.requireNonNull(data).getData();
            avatarUriString = Objects.requireNonNull(selectedImage).toString();
            Glide.with(getContext()).load(selectedImage).into(avatarPhoto);
        }
    }

    private void fireAvatarPick(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("avatarUriString",avatarUriString);
    }
}
