package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View profileLayout = findViewById(R.id.profile_layout);

        View bookingsLayout = findViewById(R.id.myBooking_layout);

        View sessionsLayout = findViewById(R.id.view_sessions_layout);

        View historyLayout = findViewById(R.id.history_layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void myProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        startActivity(profileIntent);
    }

    public void myBookings(View view) {
        Intent bookingIntent = new Intent(this, MyBookingActivity.class);
        startActivity(bookingIntent);
    }

    public void viewSessions(View view) {
        Intent sessionsIntent = new Intent(this, AvailableSessionsActivity.class);
        startActivity(sessionsIntent);
    }

    public void myHistory(View view) {
        Intent historyIntent = new Intent(this, BookingHistoryActivity.class);
        startActivity(historyIntent);
    }
}
