package com.hobbymeetingapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = findViewById(R.id.textview);

        Service service = Client.getClient().create(Service.class);
        Call<Health> healthCall = service.getHealth();
        healthCall.enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                textview.setText(response.body().getHealth());
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                textview.setText("Server is unreachable: " + t.getLocalizedMessage());
            }
        });
    }
}
