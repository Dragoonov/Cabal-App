package com.cabal.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cabal.app.Utils.JsonLoader;
import com.cabal.app.Utils.User;
import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.HobbyTypeModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddEventFragment extends Fragment {

    private static final String TAG = "AddEventFragment";
    private static final int REQUEST_CODE = 123;
    private static final String ERROR_MESSAGE_DURATION = "Incorrect duration! ";
    private static final String ERROR_MESSAGE_MEMBERS = "Incorrect number of members! ";
    private static final String ERROR_MESSAGE_START_EVENT = "Incorrect start of event! ";
    private static final String ERROR_MESSAGE_DESCRIPTION = "Incorrect description! ";
    private static final String ERROR_MESSAGE_LOCATION = "Incorrect location";
    String errorMessage = "";

    private EditText editTextDate;
    private EditText editTextStartEvent;
    private EditText editTextDuration;
    private EditText editTextPlace;
    private EditText editTextNumberOfMembers;
    private EditText editTextDescreption;
    private Button btnAddEvent;
    private Spinner spinnerSubCategories;
    ArrayAdapter<String> staticSubCategoriesAdapter;

    private final Calendar myCalendar = Calendar.getInstance();
    private boolean isDataCorrect = true;
    private int numberOfMembers;
    private String location = "";
    private String description = "";
    private int startHour;
    private int startMinute;
    private int duration;
    private String selectedHourRightFormat = "";
    private String selectedMinuteRightFormat = "";
    private String[] hobbiesNamesFirstType;
    private String[] hobbiesNamesSecondType;
    private String[] hobbiesNamesThirdType;
    private String[] hobbiesNamesToShow;
    private String chosenDate;

    String myFormat = "dd/MM/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        btnAddEvent = view.findViewById(R.id.btnAdd);

        Places.initialize(Objects.requireNonNull(getContext()), Configuration.PLACES_KEY);
        PlacesClient placesClient = Places.createClient(getContext());


        List<HobbyTypeModel> hobbyTypes = JsonLoader.loadHobbies(getContext());
        HobbyModel[] hobbiesFirstType = hobbyTypes.get(0).getHobbies();
        HobbyModel[] hobbiesSecondType = hobbyTypes.get(1).getHobbies();
        HobbyModel[] hobbiesThirdType = hobbyTypes.get(2).getHobbies();

        String[] types = new String[hobbyTypes.size()];
        hobbiesNamesFirstType = new String[hobbiesFirstType.length];
        hobbiesNamesSecondType = new String[hobbiesSecondType.length];
        hobbiesNamesThirdType = new String[hobbiesThirdType.length];

        for(int i = 0; i< hobbyTypes.size(); i++) {
            types[i] = hobbyTypes.get(i).getType();
        }

        for(int j = 0; j< hobbiesFirstType.length; j++) {
            hobbiesNamesFirstType[j] = hobbiesFirstType[j].getName();
        }

        for(int j = 0; j< hobbiesSecondType.length; j++) {
            hobbiesNamesSecondType[j] = hobbiesSecondType[j].getName();
        }

        for(int j = 0; j< hobbiesThirdType.length; j++) {
            hobbiesNamesThirdType[j] = hobbiesThirdType[j].getName();
        }

        hobbiesNamesToShow = hobbiesNamesFirstType;

        spinnerSubCategories = view.findViewById(R.id.spinner_sub_categories);

        Spinner spinnerCategories = view.findViewById(R.id.spinner_categories);
        ArrayAdapter<String> staticCategoriesAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                        android.R.layout.simple_spinner_item, types);

        staticCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(staticCategoriesAdapter);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                System.out.println("item " + parent.getItemAtPosition(position));
                setHobbiesNamesToShow(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        staticSubCategoriesAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                 android.R.layout.simple_spinner_item, hobbiesNamesToShow);

        staticSubCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategories.setAdapter(staticSubCategoriesAdapter);

        spinnerSubCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
    });

        editTextDate = view.findViewById(R.id.date);
        chosenDate = sdf.format(new Date());
        editTextDate.setText(chosenDate);

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1);
            dialog.show();
        });

        editTextStartEvent = view.findViewById(R.id.start_event);

        editTextStartEvent.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> showTimeAndSave(editTextStartEvent, selectedHour, selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        editTextDuration = view.findViewById(R.id.end_event);
        editTextPlace = view.findViewById(R.id.place);
        editTextPlace.setOnClickListener(v -> launchPlacesSearch());
        editTextDescreption = view.findViewById(R.id.description);
        editTextNumberOfMembers = view.findViewById(R.id.NumberOfMembers);

        btnAddEvent.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void launchPlacesSearch() {
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

// Start the autocomplete intent.
        Log.d(TAG, "launchPlacesSearch: ");
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setLocationRestriction(RectangularBounds.newInstance(
                        new LatLng(User.getCoordinates()[0] - calculateLatDistance(),
                                User.getCoordinates()[1] - calculateLngDistance()),
                        new LatLng(User.getCoordinates()[0] + calculateLatDistance(),
                                User.getCoordinates()[1] + calculateLngDistance())))
                .build(Objects.requireNonNull(getContext()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    private double calculateLatDistance() {
        return User.getRadius() / 110.574;
    }

    private double calculateLngDistance() {
        return User.getRadius() / (111.320 * Math.cos(Math.toRadians(User.getCoordinates()[0])));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place name: " + place.getName() +
                        ", address:" + place.getAddress() +
                        ", latlng:" + place.getLatLng());
                editTextPlace.setText(place.getName() + ", " + place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void setHobbiesNamesToShow(int position) {
        if(position == 0) {
            hobbiesNamesToShow = hobbiesNamesFirstType;
        }
        else if(position == 1) {
            hobbiesNamesToShow = hobbiesNamesSecondType;
        }
        else if(position == 2) {
            hobbiesNamesToShow = hobbiesNamesThirdType;
        }

        staticSubCategoriesAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, hobbiesNamesToShow);

        staticSubCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategories.setAdapter(staticSubCategoriesAdapter);
    }

    private void saveEvent() {

        location = editTextPlace.getText().toString();
        description = editTextDescreption.getText().toString();

        checkingIfDescriptionIsCorrect();
        checkingIfNumberOfMembersIsCorrect();
        checkingIfLocationIsCorrect();
        checkingIfDurationIsCorrect();
        checkingIfStartTimeEventIsCorrect(startHour, startMinute);

        if(isDataCorrect) {
            //save event
        }
        else {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
            errorMessage = "";
            isDataCorrect = true;
        }
    }

    private void updateLabel() {
        chosenDate = sdf.format(myCalendar.getTime());
        editTextDate.setText(chosenDate);
    }

    private void showTimeAndSave(EditText editText, int selectedHour, int selectedMinute) {
        selectedHourRightFormat = String.valueOf(selectedHour);
        selectedMinuteRightFormat = String.valueOf(selectedMinute);

        if(selectedHour < 9) {
            selectedHourRightFormat = "0" + selectedHour;
        }

        if(selectedMinute < 9) {
            selectedMinuteRightFormat = "0" + selectedMinute;
        }
        editText.setText( selectedHourRightFormat + ":" + selectedMinuteRightFormat);

        startHour = selectedHour;
        startMinute = selectedMinute;

    }

    private void checkingIfStartTimeEventIsCorrect(int startH, int startM) {
        Date today = new Date();
        int todayHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int todayMinute = myCalendar.get(Calendar.MINUTE);

        if(selectedHourRightFormat.equals("") && selectedMinuteRightFormat.equals("") && editTextStartEvent.getText().equals("")) {
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_START_EVENT;
        }
        else {
            if (chosenDate.compareTo(sdf.format(today)) == 0) {
                if ((todayHour * 60 + todayMinute) >= (startH * 60 + startM)) {
                    isDataCorrect = false;
                    errorMessage = ERROR_MESSAGE_START_EVENT;
                }
            }
        }
    }

    private void checkingIfNumberOfMembersIsCorrect() {
        try {
            numberOfMembers = Integer.parseInt(editTextNumberOfMembers.getText().toString());
            System.out.println("members " + numberOfMembers);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_MEMBERS;
        }
    }

    private void checkingIfDurationIsCorrect() {
        try {
            duration = Integer.parseInt(editTextDuration.getText().toString());
            System.out.println("Duration " + duration);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_DURATION;
        }
    }

    private void checkingIfDescriptionIsCorrect() {
        if(description.equals("")) {
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_DESCRIPTION;
        }
    }

    private void checkingIfLocationIsCorrect() {
        if(location.equals("")) {
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_LOCATION;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("startH", startHour);
        savedInstanceState.putInt("startM", startMinute);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            startHour = savedInstanceState.getInt("startH");
            startMinute = savedInstanceState.getInt("startM");
        }
    }
}