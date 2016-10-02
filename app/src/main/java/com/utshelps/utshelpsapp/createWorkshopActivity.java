package com.utshelps.utshelpsapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateWorkshopActivity extends AppCompatActivity
{
    private EditText dateField;
    private EditText locationField;
    private EditText timeField;
    private EditText topicField;
    private EditText availableField;
    private EditText codeField;
    private Button createBtn;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsession);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateField = (EditText) findViewById(R.id.dateField);
        locationField = (EditText) findViewById(R.id.locationField);
        timeField = (EditText) findViewById(R.id.timeField);
        topicField = (EditText) findViewById(R.id.topicField);
        availableField = (EditText) findViewById(R.id.availableField);
        codeField = (EditText) findViewById(R.id.codeField);
        createBtn = (Button) findViewById(R.id.createBtn);
        dataRef = FirebaseDatabase.getInstance().getReference().child("Sessions").child("Upass").child("Programming");

        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String date = dateField.getText().toString();
                String location = locationField.getText().toString();
                String time = timeField.getText().toString();
                String topic = topicField.getText().toString();
                String available = availableField.getText().toString();
                String code = codeField.getText().toString();

                DatabaseReference newSession = dataRef.push();
                newSession.child("Date").setValue(date);
                newSession.child("Location").setValue(location);
                newSession.child("Time").setValue(time);
                newSession.child("Topic").setValue(topic);
                newSession.child("Availability").setValue(available);
                newSession.child("SessionCode").setValue(code);
            }
        });
    }
}