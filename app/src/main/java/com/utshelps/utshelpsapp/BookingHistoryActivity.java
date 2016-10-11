package com.utshelps.utshelpsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingHistoryActivity extends AppCompatActivity
{
    private Button cancelBtn;
    private Firebase mRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private Firebase rootRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.bookingHistory_recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Bookings");
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Session,BookingViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Session, BookingViewHolder>(
                Session.class,
                R.layout.my_booking_item,
                BookingViewHolder.class,
                mDatabase
        )
        {
            @Override
            protected void populateViewHolder(BookingViewHolder viewHolder, Session model, int position)
            {
                boolean check = checkDate(model.getDate());
                if(check)
                {
                    viewHolder.setDate(model.getDate());
                    viewHolder.setLocation(model.getLocation());
                    viewHolder.setTime(model.getTime());
                    viewHolder.setBtn();
                }
                else
                {
                    viewHolder.deleteLayout();
                }
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    public void viewDetails(View view) {
        Intent intent = new Intent(BookingHistoryActivity.this, BookingDetailActivity.class);
        startActivity(intent);
    }

    private boolean checkDate(String date)
    {
        try
        {
            if (new SimpleDateFormat("dd/MM/yyyy").parse(date).before(new Date()))
            {
                return true;
            }
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        Button myBooking_cancelBtn;
        Button viewBooking;

        public BookingViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date)
        {
            TextView bookingDate = (TextView) mView.findViewById(R.id.myBooking_date);
            bookingDate.setText(date);
        }

        public void setTime(String time)
        {
            TextView bookingTime = (TextView) mView.findViewById(R.id.myBooking_time);
            bookingTime.setText(time);
        }

        public void setLocation(String location)
        {
            TextView bookingLocation = (TextView) mView.findViewById(R.id.myBooking_building);
            bookingLocation.setText(location);
        }

        public void setBtn()
        {
            myBooking_cancelBtn = (Button) mView.findViewById(R.id.myBooking_cancelBtn);
            viewBooking = (Button) mView.findViewById(R.id.myBooking_viewBtn);
            myBooking_cancelBtn.setVisibility(View.GONE);
            viewBooking.setVisibility(View.GONE);
        }

        public void deleteLayout()
        {
            View historyView = mView.findViewById(R.id.deleteView);
            View line = mView.findViewById(R.id.line4);
            historyView.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

    }
}