package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AvailableSessionsActivity extends AppCompatActivity {

    public static final String TITLE = "title";
    public static final String SESSION_ONE = "session_one";
    public static final String SESSION_TWO = "session_two";
    public static final String SESSION_THREE = "session_three";
    Button writingBtn;
    Button readingBtn;
    Button speakingBtn;
    Button listeningBtn;
    Button dataStructureBtn;
    Button programmingBtn;
    Button interactionDesignBtn;
    Button softwareArchitectureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readingBtn = (Button) findViewById(R.id.readingBtn);
        writingBtn = (Button) findViewById(R.id.writingBtn);
        speakingBtn = (Button) findViewById(R.id.speakingBtn);
        listeningBtn = (Button) findViewById(R.id.listeningBtn);
        dataStructureBtn = (Button) findViewById(R.id.dataStructureBtn);
        programmingBtn = (Button) findViewById(R.id.programmingBtn);
        interactionDesignBtn = (Button) findViewById(R.id.interactionDesignBtn);
        softwareArchitectureBtn = (Button) findViewById(R.id.softwareArchitectureBtn);

    }

    public void readingSession(View view) {
        String title = readingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Communication/Reading/-KSOZGSpiioiBe0xnmZX"));
        intent.putExtra(SESSION_TWO, getSession("Communication/Reading/-KSOZRaBry-_TnGagsvw"));
        intent.putExtra(SESSION_THREE, getSession("Communication/Reading/-KSOZYYmZrSaTCiRmk4T"));
        startActivity(intent);
    }

    public void writingSession(View view) {
        String title = writingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Communication/Writing/-KSOWwFjZSCDxrRB8KNA"));
        intent.putExtra(SESSION_TWO, getSession("Communication/Writing/-KSOXFVVVca8vvSFYILg"));
        startActivity(intent);
    }

    public void speakingSession(View view) {
        String title = speakingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Communication/Speaking/-KSOYCZSwyWS5lDbEgZ1"));
        intent.putExtra(SESSION_TWO, getSession("Communication/Speaking/-KSOYYdGcCA8ysNWP38x"));
        startActivity(intent);
    }

    public void listeningSession(View view) {
        String title = listeningBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(SESSION_ONE, getSession("Communication/Listening/-KSO_0Rud0ud9qTeadTj"));
        intent.putExtra(SESSION_TWO, getSession("Communication/Listening/-KSO_9Ckjn7VWUwpsjEc"));
        intent.putExtra(SESSION_THREE, getSession("Communication/Listening/-KSO_KJMYErmTO7_3FZZ"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void dataStructureSession(View view) {
        String title = dataStructureBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Upass/DSA/-KSOaV3HDb8tLH-V_iTJ"));
        intent.putExtra(SESSION_TWO, getSession("Upass/DSA/-KSOas8tk3VPS0o3_Lar"));
        startActivity(intent);
    }

    public void programmingSession(View view) {
        String title = programmingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Upass/Programming/-KSOdiGcDQp4vsQ4kO4g"));
        intent.putExtra(SESSION_TWO, getSession("Upass/Programming/-KSOdoH35dL058ZbskdQ"));
        startActivity(intent);
    }

    public void interactionDesignSession(View view) {
        String title = interactionDesignBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Upass/Interaction Design/-KSOctQAyQlU8I_sw2JZ"));
        intent.putExtra(SESSION_TWO, getSession("Upass/Interaction Design/-KSOdBxjOzLJAf72Pg-M"));
        startActivity(intent);
    }

    public void softwareArchitectureSession(View view) {
        String title = softwareArchitectureBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(SESSION_ONE, getSession("Upass/Software Architecture/-KSObxsSCy3SL3xYzrZP"));
        intent.putExtra(SESSION_TWO, getSession("Upass/Software Architecture/-KSOc51FBN2jw6DAY-xw"));
        startActivity(intent);
    }

    private String getSession(String id) {
        String link = "https://utshelps-1574c.firebaseio.com/Sessions/" + id;
        return link;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

}
