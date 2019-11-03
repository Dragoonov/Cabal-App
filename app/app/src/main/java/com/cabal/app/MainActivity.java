package com.cabal.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cabal.app.dialogs.AlertDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogListener {

    TextView textview;
    Button dialogButton;
    Button notificationButtonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textview);
        dialogButton = findViewById(R.id.quitButtonTest);
        notificationButtonTest = findViewById(R.id.notificationButtonTest);
        createNotificationChannel();

        //in Fragment, use getFragmentManager
        dialogButton.setOnClickListener(v -> new AlertDialogFragment().show(getSupportFragmentManager(),"AlertDialog"));
        notificationButtonTest.setOnClickListener(v -> createNotification());

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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        textview.setText("Accepted!");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        textview.setText("Rejected!");
    }

    private void createNotification() {
        Intent intent = new Intent(getApplicationContext(),AfterRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.drawable.expand_arrow)
                .setContentTitle("Tytul notyfikacji")
                .setContentText("Przejdz do AfterRegister")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channeldescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}
