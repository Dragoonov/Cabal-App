package com.cabal.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabal.app.Utils.BackendCommunicator;
import com.cabal.app.Utils.User;
import com.cabal.app.navigation_bar.UserActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WelcomeActivity";
    static private Service service = Client.getClient().create(Service.class);
    private static final int REQUEST_CODE = 123;
    EditText email;
    EditText password;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        User.instantiate();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        explanation = findViewById(R.id.explanation);
        explanation.setVisibility(View.INVISIBLE);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkPermission();
        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    private void signIn() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        postLoginData(emailString, passwordString);
    }

    private void signUp() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        if (validate(emailString, passwordString)) {
            postRegisterData(emailString, passwordString);
        }
    }

    private boolean validate(String email, String password) {
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            signIn();
        }
        if (view.getId() == R.id.sign_up_button) {
            signUp();
        }
    }

    private void postLoginData(String username, String password) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("username", username);
        requestBody.addProperty("password", password);
        Call<JsonObject> tokenCall = service.postLoginData(requestBody);
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                    User.setTokenId(response.body().get("token").getAsString());
                    boolean firstTime = response.body().get("firstTime").getAsBoolean();
                    if(firstTime){
                        startActivity(new Intent(WelcomeActivity.this,AfterRegisterActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(WelcomeActivity.this, UserActivity.class));
                        finish();
                    }
                }
                else {
                    Log.d(TAG, "onResponse: " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    private void postRegisterData(String username, String password) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("username", username);
        requestBody.addProperty("password", password);
        Call<JsonObject> tokenCall = service.postRegisterData(requestBody);
        tokenCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Zarejestrowano!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    /*private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                BackendCommunicator.postUserDataToBackend(account.getIdToken(), this);
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }*/

    public void checkPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    explanation.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        User.setCoordinates(new double[]{location.getLatitude(), location.getLongitude()});
                    }
                }).addOnFailureListener(this, e -> Log.d(TAG, "onCreate: fail" + e.getMessage()));
    }

}
