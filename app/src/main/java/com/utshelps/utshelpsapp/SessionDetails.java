package com.utshelps.utshelpsapp;

import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;

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
    private DatabaseReference dataRef;

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

        LinePageIndicator linePageIndicator = (LinePageIndicator) findViewById(R.id.session_swipingIndicator);
        linePageIndicator.setViewPager(mViewPager);

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
        String reminderTime = "";

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
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_session_details, container, false);
            TextView titleTv = (TextView) rootView.findViewById(R.id.session_title);
            String titleString = getActivity().getIntent().getStringExtra(AvailableSessionsActivity.TITLE);
            titleTv.setText(titleString);

            String link = "";

            final TextView dateTv = (TextView) rootView.findViewById(R.id.session_date);
            final TextView timeTv = (TextView) rootView.findViewById(R.id.session_time);
            final TextView locationTv = (TextView) rootView.findViewById(R.id.session_location);
            final TextView topicTv = (TextView) rootView.findViewById(R.id.session_topic);
            final Button bookBtn = (Button) rootView.findViewById(R.id.session_bookBtn);


            /*try {
                Spinner timeSpinner = (Spinner) rootView.findViewById(R.id.reminder_timeSpinner);
                ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.time, android.R.layout.simple_spinner_item);
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                timeSpinner.setAdapter(timeAdapter);

                Spinner ampmSpinner = (Spinner) rootView.findViewById(R.id.reminder_ampmSpinner);
                ArrayAdapter<CharSequence> ampmAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ampm, android.R.layout.simple_spinner_item);
                ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ampmSpinner.setAdapter(ampmAdapter);
            }
            catch (Exception e) {
                e.printStackTrace();
            }*/

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

            try
            {
                final Firebase rootRef = new Firebase(link);

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
                        final String staff = map.get("Staff");
                        final String bookKey = dataSnapshot.getKey();
                        Log.v("key", bookKey);
                        final Map<String, Integer> mapInt = dataSnapshot.getValue(Map.class);
                        final int slot = mapInt.get("Slot");
                        final int queue = mapInt.get("Queue");
                        dateTv.setText(date);
                        timeTv.setText(time);
                        locationTv.setText(location);
                        topicTv.setText(topic);

                        bookBtn.setOnClickListener(new View.OnClickListener() {
                            public static final String SESSIONDETAILS = "SessionDetails";

                            @Override
                            public void onClick(View v) {
                                Log.d(SESSIONDETAILS, "onClick: bookBtn");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                final String uid = user.getUid();
                                if (slot > 0) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle(R.string.confirm);
                                    builder.setMessage(R.string.when_reminded);

                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                    View promptView = layoutInflater.inflate(R.layout.reminder, null);

                                    builder.setView(promptView);

                                    final RadioButton emailRadioBtn = (RadioButton) promptView.findViewById(R.id.reminder_emailRadioBtn);
                                    final RadioButton smsRadioBtn = (RadioButton) promptView.findViewById(R.id.reminder_smsRadioBtn);

                                    emailRadioBtn.setChecked(true);

                                    final Spinner reminderSpinner = (Spinner) promptView.findViewById(R.id.reminder_spinner);
                                    ArrayAdapter<CharSequence> reminderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.reminder, android.R.layout.simple_spinner_item);
                                    reminderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    reminderSpinner.setAdapter(reminderAdapter);

                                    reminderSpinner.setSelection(1);

                                    reminderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        public static final String BOOKING_DETAIL_ACTIVITY = "BookingDetailActivity";

                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            Log.d(BOOKING_DETAIL_ACTIVITY, "reminderSpinner");
                                            reminderTime = reminderSpinner.getSelectedItem().toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    builder.setCancelable(true);

                                    builder.setPositiveButton(R.string.confirm_alert, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Bookings");
                                            DatabaseReference newSession = dataRef.child(bookKey);
//                                            DatabaseReference newSession = dataRef.push();
                                            newSession.child("Date").setValue(date);
                                            newSession.child("Time").setValue(time);
                                            newSession.child("Location").setValue(location);
                                            newSession.child("Topic").setValue(topic);
                                            newSession.child("SessionCode").setValue(sessionCode);
                                            newSession.child("Type").setValue(type);
                                            newSession.child("attendanceRecorded").setValue("false");
                                            newSession.child("reminderTime").setValue(reminderTime);
                                            newSession.child("reminderDate").setValue("12/10/2016");
                                            if(smsRadioBtn.isChecked()) {
                                                newSession.child("reminderType").setValue("sms");
                                            }
                                            else
                                            {
                                                newSession.child("reminderType").setValue("email");
                                            }
                                            newSession.child("Staff").setValue(staff);

                                            Map<String, Object> mapObject = new HashMap<>();
                                            mapObject.put("Slot", slot - 1);
                                            rootRef.updateChildren(mapObject);

                                            Toast.makeText(getContext(), "Booking successful", Toast.LENGTH_LONG).show();
                                        }


                                    });
                                    builder.create();
                                    builder.show();
                                }
                                if (slot == 0) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle(R.string.session_full);
                                    builder.setMessage("This session has reached max capacity, would you like to join the waitlist?"+ "current queue now:" + queue);


                                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    builder.setCancelable(true);

                                    builder.setNegativeButton(R.string.join_waitlist, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Waitlist");
                                            DatabaseReference newSession = dataRef.child(bookKey);
//                                          DatabaseReference newSession = dataRef.push();
                                            newSession.child("Date").setValue(date);
                                            newSession.child("Time").setValue(time);
                                            newSession.child("Location").setValue(location);
                                            newSession.child("Topic").setValue(topic);
                                            newSession.child("SessionCode").setValue(sessionCode);
                                            newSession.child("Type").setValue(type);
//                                          newSession.child("attendanceRecorded").setValue("false");
//                                          newSession.child("reminderTime").setValue("11");
//                                          newSession.child("reminderDate").setValue("12/10/2016");
//                                          newSession.child("reminderType").setValue("email");

                                            Toast.makeText(getContext(), R.string.added_to_waitlist, Toast.LENGTH_LONG).show();
                                        }


                                    });
                                    builder.create();
                                    builder.show();
                                }
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
        }

        @Override
        public int getCount() {
            // Amount of pages to be shown
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

        public void createAssignment() {


        }
    }
}