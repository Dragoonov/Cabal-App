package com.cabal.app;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cabal.app.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private String password = "";
    static private Service service = Client.getClient().create(Service.class);

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputConfirmPassword = findViewById(R.id.text_input_confirm_password);
        buttonRegister = findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(v -> {
            confirmInput(v);
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak! At least: 1 lower case letter, 1 upper case letter," +
                    " 1 digit, 4 characters");
            return false;
        } else {
            password = passwordInput;
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirmedPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (!password.equals(confirmedPasswordInput)) {
            textInputConfirmPassword.setError("Passwords must be the same");
            return false;
        } else {
            textInputConfirmPassword.setError(null);
            return true;
        }
    }



    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        String emailString = textInputEmail.getEditText().getText().toString();
        String passwordString = textInputPassword.getEditText().getText().toString();

        postRegisterData(emailString, passwordString);
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
}
