package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by timothyalfares on 9/26/2016.
 */
public class RegisterActivity extends AppCompatActivity
{
    private EditText contactField;
    private EditText fLanguageField;
    private EditText countryField;
    private EditText yearField;
    private RadioGroup radioDegreeGroup;
    private RadioButton radioDegreeButton;
    private RadioGroup radioStatusGroup;
    private RadioButton radioStatusButton;
    private Button registerBtn;
    private DatabaseReference dataRef;
    private Firebase rootRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactField = (EditText) findViewById(R.id.contactField);
        fLanguageField = (EditText) findViewById(R.id.fLanguageField);
        countryField = (EditText) findViewById(R.id.countryField);
        yearField = (EditText) findViewById(R.id.yearField);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        radioDegreeGroup = (RadioGroup) findViewById(R.id.radioDegreeGroup);
        radioStatusGroup = (RadioGroup) findViewById(R.id.radioStatusGroup);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                    registerBtn.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            updateUser();
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                    });
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void updateUser()
    {
        String contact = contactField.getText().toString();
        String language = fLanguageField.getText().toString();
        String country = countryField.getText().toString();
        String year = yearField.getText().toString();
        int selectedDegree = radioDegreeGroup.getCheckedRadioButtonId();
        int selectedStatus = radioStatusGroup.getCheckedRadioButtonId();
        radioDegreeButton = (RadioButton) findViewById(selectedDegree);
        radioStatusButton = (RadioButton) findViewById(selectedStatus);
        String degree = radioDegreeButton.getText().toString();
        String status = radioStatusButton.getText().toString();
        if (TextUtils.isEmpty(contact) || TextUtils.isEmpty(language) || TextUtils.isEmpty(country))
        {
            Toast.makeText(RegisterActivity.this, "Please enter all details!", Toast.LENGTH_LONG).show();
            return;
        }

        dataRef.child("contactNum").setValue(contact);
        dataRef.child("firstLanguage").setValue(language);
        dataRef.child("countryOrigin").setValue(country);
        dataRef.child("studyYear").setValue(year);
        dataRef.child("degree").setValue(degree);
        dataRef.child("status").setValue(status);
    }

    public void onRadioButtonClicked(View view) {
        
    }
}