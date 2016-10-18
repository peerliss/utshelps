package com.utshelps.utshelpsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class BookingDetailActivity extends AppCompatActivity {

    Button reminderBtn;
    EditText attendanceText;
    Button attendanceBtn;
    final Context context = this;
    TextView attendanceTv;
    TextView bookingDetail_date;
    TextView bookingDetail_time;
    TextView bookingDetail_location;
    TextView bookingDetail_type;
    private Firebase fRoot;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String link;
    String code;
    String checkAttendance = "no";
    String reminderType = "email";
    String reminderTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("stuff");
        fRoot = new Firebase(link);
        mAuth = FirebaseAuth.getInstance();
        setReference();

        fRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    getData(map);
                    if ((checkAttendance.equals("false") || checkAttendance.equals("no")) || checkAttendance.isEmpty()) {
                        attendanceBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Please enter workshop code");
                                LayoutInflater layoutInflater = LayoutInflater.from(context);
                                View promptView = layoutInflater.inflate(R.layout.attendance, null);
                                builder.setView(promptView);
                                attendanceText = (EditText) promptView.findViewById(R.id.attendance_input);

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), "Please record attendance", Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.setCancelable(true);

                                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String aText = attendanceText.getText().toString();
                                        if (code.equals(aText)) {
                                            Map<String, Object> taskMap = new HashMap<String, Object>();
                                            taskMap.put("attendanceRecorded", "yes");
                                            fRoot.updateChildren(taskMap);
                                            Toast.makeText(getApplicationContext(), "Attendance Recorded", Toast.LENGTH_LONG).show();
                                            attendanceBtn.setVisibility(View.GONE);
                                            attendanceTv.setVisibility(View.VISIBLE);
                                        } else
                                            Toast.makeText(getApplicationContext(), "Wrong session code", Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.create();
                                builder.show();
                            }
                        });
                    } else {
                        attendanceBtn.setVisibility(View.GONE);
                        attendanceTv.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookingDetailActivity.this);
                final LayoutInflater layoutInflater = LayoutInflater.from(BookingDetailActivity.this);
                final View reminderView = layoutInflater.inflate(R.layout.reminder, null);

                final Spinner reminderSpinner = (Spinner) reminderView.findViewById(R.id.reminder_spinner);
                ArrayAdapter<CharSequence> reminderAdapter = ArrayAdapter.createFromResource(BookingDetailActivity.this, R.array.reminder, android.R.layout.simple_spinner_item);
                reminderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                reminderSpinner.setAdapter(reminderAdapter);

                reminderSpinner.setSelection(1);

                alertDialogBuilder.setTitle("Do you want to get informed by Email or SMS?");

                reminderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public static final String BOOKING_DETAIL_ACTIVITY = "BookingDetailActivity";

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(BOOKING_DETAIL_ACTIVITY, "reminderSpinner");
                        reminderTime = reminderSpinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                RadioGroup reminderTypeGroup = (RadioGroup) reminderView.findViewById(R.id.reminder_radioGroup);
                int selectedId = reminderTypeGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = (RadioButton) reminderView.findViewById(selectedId);
                final String selectedRadioButton = "";

                final RadioButton emailRadioBtn = (RadioButton) reminderView.findViewById(R.id.reminder_emailRadioBtn);
                final RadioButton smsRadioBtn = (RadioButton) reminderView.findViewById(R.id.reminder_smsRadioBtn);

                emailRadioBtn.setChecked(true);

                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setView(reminderView).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (smsRadioBtn.isChecked()) {
                            Map<String, Object> taskMap = new HashMap<String, Object>();
                            taskMap.put("reminderType", "sms");
                            taskMap.put("reminderTime", reminderTime);
                            fRoot.updateChildren(taskMap);
                            Toast.makeText(getApplicationContext(), "You will be reminded via SMS", Toast.LENGTH_LONG).show();
                        } else if (emailRadioBtn.isChecked()) {
                            Map<String, Object> taskMap = new HashMap<String, Object>();
                            taskMap.put("reminderType", "email");
                            taskMap.put("reminderTime", reminderTime);
                            fRoot.updateChildren(taskMap);
                            Toast.makeText(getApplicationContext(), "You will be reminded via Email", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void setReference() {
        bookingDetail_date = (TextView) findViewById(R.id.bookingDetail_date);
        bookingDetail_time = (TextView) findViewById(R.id.bookingDetail_time);
        bookingDetail_location = (TextView) findViewById(R.id.bookingDetail_location);
        bookingDetail_type = (TextView) findViewById(R.id.bookingDetail_type);
        reminderBtn = (Button) findViewById(R.id.bookingDetail_reminderBtn);
        attendanceBtn = (Button) findViewById(R.id.bookingDetail_attendanceBtn);
        attendanceTv = (TextView) findViewById(R.id.attendance_recordedTv);
    }

    public void getData(Map<String, String> map) {
        String type = map.get("Type");
        String location = map.get("Location");
        String date = map.get("Date");
        String time = map.get("Time");
        code = map.get("SessionCode");
        checkAttendance = map.get("attendanceRecorded");
        reminderType = map.get("reminderType");
        bookingDetail_type.setText(type);
        bookingDetail_location.setText(location);
        bookingDetail_date.setText(date);
        bookingDetail_time.setText(time);
    }

}
