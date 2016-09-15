package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText studentID;
    private EditText studentPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        studentID = (EditText) findViewById(R.id.student_id);
        studentPassword = (EditText) findViewById(R.id.student_password);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }*/

    public void Login(View view) {
        String stringID = studentID.getText().toString();
        String stringPassword = studentPassword.getText().toString();
        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(loginIntent);

        /*if (stringID == "12345678"*//* || stringPassword == "12345678"*//*) {
            startActivity(loginIntent);
        }
        else
            Toast.makeText(MainActivity.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();*/
    }
}
