package com.codefluid.calificarapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertRateShow;
    private Preferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = new Preferences(this);
        showRate();
    }

    private void showRate() {
        if (!p.getVersion().equals(BuildConfig.VERSION_NAME)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View alertRate = getLayoutInflater().inflate(R.layout.alert_rate, null);
            RatingBar ratingBar = alertRate.findViewById(R.id.rbRate);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    try {
                        p.keepRate(BuildConfig.VERSION_NAME);
                        alertRateShow.dismiss();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }
            });

            builder.setView(alertRate);
            alertRateShow = builder.create();
            alertRateShow.show();
        }
    }

}
