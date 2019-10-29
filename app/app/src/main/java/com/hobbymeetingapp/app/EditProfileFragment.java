package com.hobbymeetingapp.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hobbymeetingapp.app.hobbies_edit_list.Hobbies;

public class EditProfileFragment extends Fragment {

    TextView seekBarDistance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        SeekBar seekBar = view.findViewById(R.id.seekBarRadius);
        seekBar.setOnSeekBarChangeListener(seekBarRadiusChangeListener);

        int progress = seekBar.getProgress();
        seekBarDistance = view.findViewById(R.id.distance);
        seekBarDistance.setText("" + progress);

        return view;
    }

    SeekBar.OnSeekBarChangeListener seekBarRadiusChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            seekBarDistance.setText("" + progress);
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

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState == null){
            Hobbies.initializeHobbies(getActivity());
        }
    }
}
