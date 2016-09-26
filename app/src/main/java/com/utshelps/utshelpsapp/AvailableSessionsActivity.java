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
        startActivity(intent);
    }

    public void writingSession(View view) {
        String title = writingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void speakingSession(View view) {
        String title = speakingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void listeningSession(View view) {
        String title = listeningBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void dataStructureSession(View view) {
        String title = dataStructureBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void programmingSession(View view) {
        String title = programmingBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void interactionDesignSession(View view) {
        String title = interactionDesignBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void softwareArchitectureSession(View view) {
        String title = softwareArchitectureBtn.getText().toString();
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

}
