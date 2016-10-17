package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by timothyalfares on 10/16/2016.
 */
public class AllSessionsActivity extends AppCompatActivity
{
    public static final String SESSION_ONE = "session_one";
    public static final String SESSION_TWO = "session_two";
    public static final String SESSION_THREE = "session_three";
    public static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View writeSupport = findViewById(R.id.writeSupport);

        View nursing = findViewById(R.id.nursing);

        View upass = findViewById(R.id.upass);

        View upasscmitp = findViewById(R.id.upasscmitp);

        View upassmpo = findViewById(R.id.upassmpo);
    }

    public void writeSupport(View view) {
        String title = "Writing Support";
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(SESSION_ONE, getSession("-KU9qgHKq8uakMTGVfxc"));
        intent.putExtra(SESSION_TWO, getSession("-KUA2tPqZP-qdHLfpEm0"));
        //intent.putExtra(SESSION_THREE, getSession("Communication/Reading/-KSOZYYmZrSaTCiRmk4T"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void nursing(View view) {
        String title = "Nursing";
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(SESSION_ONE, getSession("-KU9rGdQ-dJx96lG-v6A"));
        intent.putExtra(SESSION_TWO, getSession("-KUA3QM36WFnR__zS_lE"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void upass(View view) {
        Intent intent = new Intent(this, SessionDetails.class);
        String title = "UPASS";
        intent.putExtra(SESSION_ONE, getSession("Upass/Interaction Design/-KSOctQAyQlU8I_sw2JZ"));
        intent.putExtra(SESSION_TWO, getSession("Upass/Interaction Design/-KSOdBxjOzLJAf72Pg-M"));
        intent.putExtra(SESSION_THREE, getSession("Upass/DSA/-KSOaV3HDb8tLH-V_iTJ"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void upasscmitp(View view) {
        String title = "Upass CITP";
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(SESSION_ONE, getSession("-KU9rq1OT7KUzPiOUO47"));
        intent.putExtra(SESSION_TWO, getSession("-KU9s0-a_iT_ZEOAxCtv"));
        intent.putExtra(SESSION_THREE, getSession("-KUA3mVH4-MVQAUItV2V"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    public void upassmpo(View view) {
        String title = "UPASS MPO";
        Intent intent = new Intent(this, SessionDetails.class);
        intent.putExtra(SESSION_ONE, getSession("-KU9sQRCl8D0aCFYFg0I"));
        intent.putExtra(SESSION_TWO, getSession("-KU9sY1NI3Jl1dXxaBzE"));
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    private String getSession(String id) {
        String link = "https://utshelps-1574c.firebaseio.com/Sessions/" + id;
        return link;
    }

}
