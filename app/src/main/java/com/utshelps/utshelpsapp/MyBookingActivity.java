package com.utshelps.utshelpsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyBookingActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Firebase mRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private String uid;
    int slot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.myBooking_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Bookings");
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
                    final Firebase rootRef = new Firebase("https://utshelps-1574c.firebaseio.com/Sessions" + "/" + bookKey);

                    try {
                        rootRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final Map<String, Integer> mapInt = dataSnapshot.getValue(Map.class);
                                slot = mapInt.get("Slot");
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mDatabase.child(bookKey).removeValue();

                                    Log.v("Slot", slot + "");
                                    Map<String, Object> mapObject = new HashMap<>();
                                    mapObject.put("Slot", slot += 1);
                                    rootRef.updateChildren(mapObject);
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
            View deleteView = mView.findViewById(R.id.deleteView);
            View deleteLine = mView.findViewById(R.id.line4);
            deleteView.setVisibility(View.GONE);
            deleteLine.setVisibility(View.GONE);
        }
    }
}