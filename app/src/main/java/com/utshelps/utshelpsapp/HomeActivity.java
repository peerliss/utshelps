package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View profileLayout = findViewById(R.id.profile_layout);
        profileLayout.setOnClickListener(this);

        View bookingsLayout = findViewById(R.id.myBooking_layout);
        bookingsLayout.setOnClickListener(this);

        View sessionsLayout = findViewById(R.id.view_sessions_layout);
        sessionsLayout.setOnClickListener(this);

        View historyLayout = findViewById(R.id.history_layout);
        historyLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.profile_layout:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
            case R.id.myBooking_layout:
                Intent bookingInent = new Intent(this, MyBookingActivity.class);
                startActivity(bookingInent);
            case R.id.view_sessions_layout:
                Intent sessionsIntent = new Intent(this, AvailableSessionsActivity.class);
                startActivity(sessionsIntent);
            case R.id.history_layout:
                Intent historyIntent = new Intent(this, BookingHistoryActivity.class);
                startActivity(historyIntent);
        }

    }
}
