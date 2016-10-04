package com.utshelps.utshelpsapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class SessionDetails extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    //    private Firebase rootRef;
    private FirebaseAuth mAuth;

//    private TextView dateTv;
//    private TextView timeTv;
//    private TextView locationTv;
//    private TextView topicTv;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int position;
        private FirebaseAuth.AuthStateListener mAuth;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            position = sectionNumber;

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_session_details, container, false);
            TextView titleTv = (TextView) rootView.findViewById(R.id.session_title);
            String titleString = getActivity().getIntent().getStringExtra(AvailableSessionsActivity.TITLE);
            titleTv.setText(titleString);

            String link = "";

            final TextView dateTv = (TextView) rootView.findViewById(R.id.session_date);
            final TextView timeTv = (TextView) rootView.findViewById(R.id.session_time);
            final TextView locationTv = (TextView) rootView.findViewById(R.id.session_location);
            final TextView topicTv = (TextView) rootView.findViewById(R.id.session_topic);
            final Button bookBtn = (Button) rootView.findViewById(R.id.session_bookBtn);


            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    link = getActivity().getIntent().getStringExtra(AvailableSessionsActivity.SESSION_ONE);
                    break;
                case 2:
                    link = getActivity().getIntent().getStringExtra(AvailableSessionsActivity.SESSION_TWO);
                    break;
                case 3:
                    link = getActivity().getIntent().getStringExtra(AvailableSessionsActivity.SESSION_THREE);
                    break;
            }

            try {
                Firebase rootRef = new Firebase(link);

                rootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                        final String date = map.get("Date");
                        final String time = map.get("Time");
                        final String location = map.get("Location");
                        final String topic = map.get("Topic");
                        final String sessionCode = map.get("SessionCode");
                        final String type = map.get("Type");
                        dateTv.setText(date);
                        timeTv.setText(time);
                        locationTv.setText(location);
                        topicTv.setText(topic);

                        bookBtn.setOnClickListener(new View.OnClickListener() {
                            public static final String SESSIONDETAILS = "SessionDetails";

                            @Override
                            public void onClick(View v) {
                                Log.d(SESSIONDETAILS, "onClick: bookBtn");
                                mAuth = new FirebaseAuth.AuthStateListener() {
                                    @Override
                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        if (user != null) {
                                            // User is signed in
                                            Firebase userBookingRef = new Firebase(getUser(user.getUid()));
                                            Map<String, Object> bookingMap = new HashMap<String, Object>();
                                            bookingMap.put("Date", date);
                                            bookingMap.put("Time", time);
                                            bookingMap.put("Location", location);
                                            bookingMap.put("Topic", topic);
                                            bookingMap.put("SessionCode", sessionCode);
                                            bookingMap.put("Type", type);
                                            userBookingRef.updateChildren(bookingMap);
                                            userBookingRef.push();
                                        }
                                    }

                                    private String getUser(String id) {
                                        String userId = "https://utshelps-1574c.firebaseio.com/Users/" + id + "/Bookings";
                                        return userId;
                                    }
                                };
                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
            /*switch (position) {
                case 0:
                    return PlaceholderFragment.newInstance(position + 1);
                case 1:
                    return PlaceholderFragmentTwo.newInstance(position + 1);
                case 2:
                    return PlaceholderFragmentThree.newInstance(position);
            }
            return null;*/
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            if (getIntent().getStringExtra(AvailableSessionsActivity.SESSION_THREE) != null) {
                return 3;
            } else
                return 2;
        }

        /*@Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    link = getIntent().getStringExtra(AvailableSessionsActivity.SESSION_ONE);
                    return "SECTION 1";
                case 1:
                    link = getIntent().getStringExtra(AvailableSessionsActivity.SESSION_TWO);
                    return "SECTION 2";
                case 2:
                    link = getIntent().getStringExtra(AvailableSessionsActivity.SESSION_THREE);
                    return "SECTION 3";
            }
            return null;
        }*/
    }
}
