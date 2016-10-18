package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText profileName;
    private EditText profileId;
    private EditText profileAddress;
    private EditText profileDOB;
    String link = "";
    private Button editProfile_button;
    private Button submitButton;
    private Firebase rootRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        profileName = (EditText) findViewById(R.id.profileName);
        profileName.setEnabled(false);
        profileId = (EditText) findViewById(R.id.profileId);
        profileId.setEnabled(false);
        profileAddress = (EditText) findViewById(R.id.profileAddress);
        profileAddress.setEnabled(false);
        profileDOB = (EditText) findViewById(R.id.profileDOB);
        profileDOB.setEnabled(false);
        editProfile_button = (Button) findViewById(R.id.editProfile_button);
        submitButton = (Button) findViewById(R.id.submitButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        link = getUser(user.getUid());
        rootRef = new Firebase(link);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    String name = map.get("Name");
                    String id = map.get("Email");
                    String address = map.get("Address");
                    String dob = map.get("DOB");
                    profileName.setText(name);
                    profileDOB.setText(dob);
                    profileAddress.setText(address);
                    profileId.setText(id);
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        });
        editProfile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private String getUser(String id) {
        String link = "https://utshelps-1574c.firebaseio.com/Users/" + id;
        return link;
    }

    private void editProfile() {
        profileName.setEnabled(true);
        profileId.setEnabled(true);
        profileAddress.setEnabled(true);
        profileDOB.setEnabled(true);
        editProfile_button.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }

    private void submitProfile() {
        profileName.setEnabled(false);
        profileId.setEnabled(false);
        profileAddress.setEnabled(false);
        profileDOB.setEnabled(false);
        editProfile_button.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("Name", profileName.getText().toString());
        taskMap.put("Address", profileAddress.getText().toString());
        taskMap.put("Email", profileId.getText().toString());
        taskMap.put("DOB", profileDOB.getText().toString());
        rootRef.updateChildren(taskMap);
        Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
    }
}