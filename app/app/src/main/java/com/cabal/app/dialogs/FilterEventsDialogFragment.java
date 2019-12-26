package com.cabal.app.dialogs;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cabal.app.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class FilterEventsDialogFragment extends DialogFragment {

    private AlertDialogListener listener;

    public String getEventName() {
        return eventName;
    }

    private String eventName;

    public static FilterEventsDialogFragment newInstance(String eventName, AlertDialogListener listener){
        FilterEventsDialogFragment fragment = new FilterEventsDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", eventName);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    public interface AlertDialogListener {
        void onDialogPositiveClick(FilterEventsDialogFragment dialog);
        void onDialogNegativeClick(FilterEventsDialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setMessage(getString(R.string.filterResult, eventName))
                .setPositiveButton(R.string.accept, (dialog, id) -> listener.onDialogPositiveClick(this))
                .setNegativeButton(R.string.reject, (dialog, id) -> listener.onDialogNegativeClick(this));
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventName = Objects.requireNonNull(getArguments()).getString("name");
    }
}