package com.utshelps.utshelpsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBookingActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Firebase mRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.myBooking_recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Bookings");

        /*
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Session, BookingViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Session, BookingViewHolder>(
                Session.class,
                R.layout.my_booking_item,
                BookingViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(BookingViewHolder viewHolder, Session model, int position) {
                boolean check = checkDate(model.getDate());

                if (check) {
                    viewHolder.setDate(model.getDate());
                    viewHolder.setLocation(model.getLocation());
                    viewHolder.setTime(model.getTime());
                    viewHolder.setBtn();
                    final String key = getRef(position).toString();
                    final String bookKey = getRef(position).getKey();
                    viewHolder.viewBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MyBookingActivity.this, BookingDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("stuff", key);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    viewHolder.myBooking_cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyBookingActivity.this);
                            alertDialog.setTitle("Are you sure you want to cancel your booking?");
                            alertDialog.setCancelable(true);
                            alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mDatabase.child(bookKey).removeValue();
                                    Toast.makeText(getApplicationContext(), "You have successfully been withdrawn", Toast.LENGTH_LONG).show();
                                }
                            });
                            alertDialog.create();
                            alertDialog.show();
                        }
                    });
                } else {
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
        Intent intent = new Intent(MyBookingActivity.this, BookingDetailActivity.class);
        startActivity(intent);
    }

    private boolean checkDate(String date) {
        try {
            if (new SimpleDateFormat("dd/MM/yyyy").parse(date).after(new Date())) {
                return true;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button myBooking_cancelBtn;
        Button viewBooking;

        public BookingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date) {
            TextView bookingDate = (TextView) mView.findViewById(R.id.myBooking_date);
            bookingDate.setText(date);
        }

        public void setTime(String time) {
            TextView bookingTime = (TextView) mView.findViewById(R.id.myBooking_time);
            bookingTime.setText(time);
        }

        public void setLocation(String location) {
            TextView bookingLocation = (TextView) mView.findViewById(R.id.myBooking_building);
            bookingLocation.setText(location);
        }

        public void setBtn() {
            viewBooking = (Button) mView.findViewById(R.id.myBooking_viewBtn);
            myBooking_cancelBtn = (Button) mView.findViewById(R.id.myBooking_cancelBtn);
        }

        public void deleteLayout() {
//            View deleteView = mView.findViewById(R.id.myBooking_recyclerView);
            View deleteView = mView.findViewById(R.id.deleteView);
            View deleteLine = mView.findViewById(R.id.line4);
            deleteView.setVisibility(View.GONE);
            deleteLine.setVisibility(View.GONE);
        }

    }

}