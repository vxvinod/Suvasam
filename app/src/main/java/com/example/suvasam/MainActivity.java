package com.example.suvasam;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.suvasam.database.EventFirebase;
import com.example.suvasam.model.Events;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DonateFragment.OnFragmentInteractionListener,
        EventsFragment.OnFragmentInteractionListener, AwarenessFragment.OnFragmentInteractionListener
{


    final Fragment fragment1 = new DonateFragment();
    final Fragment fragment2 = new EventsFragment();
    final Fragment fragment3 = new AwarenessFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
//                    //Test Firebase Access
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference();
//
//                    Events event = new Events(2, "Thursday Event",
//                                    "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                                  "Thursday plantation event is planned to plant 1100 trees",
//                                    "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event.id)).setValue(event);
//
//                    Events event1 = new Events(3, "Friday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Friday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event1.id)).setValue(event1);
//
//                    Events event2 = new Events(4, "Saturday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Saturday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event2.id)).setValue(event2);
//
//                    Events event3 = new Events(5, "Sunday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Sunday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event3.id)).setValue(event3);
//
//                    Events event4 = new Events(6, "Monday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Monday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event4.id)).setValue(event4);
//
//                    Events event5 = new Events(7, "Tuesday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Tuesday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event5.id)).setValue(event5);
//
//                    Events event6 = new Events(8, "Wednesday Event",
//                            "http://www.mendhamtownship.org/images/Clip%20Art/tree.jpg",
//                            "Thursday plantation event is planned to plant 1100 trees",
//                            "26-sep-2019");
//
//                    myRef.child("events").child(String.valueOf(event6.id)).setValue(event6);

                    return true;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;
                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    EventFirebase.fetchDataFromFirebase();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
