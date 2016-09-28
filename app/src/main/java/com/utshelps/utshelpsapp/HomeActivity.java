package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_contact:
                Intent intentContact = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(intentContact);
                break;
            case R.id.action_logout:
                startSignOut();
                break;
            case R.id.action_about:
                Intent intentAbout = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intentAbout);
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSignOut() {
        mAuth.signOut();
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
