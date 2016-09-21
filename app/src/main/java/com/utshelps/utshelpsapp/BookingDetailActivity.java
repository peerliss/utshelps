package com.utshelps.utshelpsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookingDetailActivity extends AppCompatActivity {

    Button reminderBtn;
    EditText attendanceText;
    Button attendanceBtn;
    final Context context = this;
    TextView attendanceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reminderBtn = (Button) findViewById(R.id.bookingDetail_reminderBtn);
        attendanceBtn = (Button) findViewById(R.id.bookingDetail_attendanceBtn);

        attendanceTv = (TextView) findViewById(R.id.attendance_recordedTv);


        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookingDetailActivity.this);
                alertDialog.setTitle("Do you want to get informed by Email or SMS?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "You will be reminded via SMS", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNegativeButton("Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "You will be reminded via Email", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });


        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please enter workshop code");
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.attendance, null);
                builder.setView(promptView);
                attendanceText = (EditText) promptView.findViewById(R.id.attendance_input);

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Please record attendance", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setCancelable(true);

                builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Attendance Recorded", Toast.LENGTH_LONG).show();
                            attendanceBtn.setVisibility(View.GONE);
                            attendanceTv.setVisibility(View.VISIBLE);
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }
}
