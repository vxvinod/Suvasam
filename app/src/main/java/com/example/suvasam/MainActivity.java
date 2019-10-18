package com.example.suvasam;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.suvasam.database.DonateFirebase;
import com.example.suvasam.database.EventFirebase;
import com.example.suvasam.model.Donate;
import com.example.suvasam.model.Events;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DonateFragment.OnFragmentInteractionListener,
        EventsFragment.OnFragmentInteractionListener, AwarenessFragment.OnFragmentInteractionListener
{


    final Fragment fragment1 = new DonateFragment();
    final Fragment fragment2 = new EventsFragment();
    final Fragment fragment3 = new AwarenessFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    private InterstitialAd mInterstitialAd;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
               //   fetchDataFromFirebase();
////                    Bundle bundle = new Bundle();
////                    bundle.putParcelableArrayList("areaList", mAreaList);
////                    fragment1.setArguments(bundle);
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
                    if(mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6506911315414653/6509638653");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    public  ArrayList<Donate> fetchDataFromFirebase() {
        final ArrayList<Donate> areaList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count", ""+dataSnapshot.getChildrenCount());
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Donate areaDetails = postSnapshot.getValue(Donate.class);
                    areaList.add(areaDetails);
                    Log.e("Get Data", areaDetails.getName());
                }

                Fragment donateFragment = new DonateFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("areaList", areaList);
                donateFragment.setArguments(bundle);
                ft.hide(active).show(donateFragment).commit();
                active = donateFragment;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        for(Donate area : areaList) {
            Log.e("DONATE FIREBASE", String.valueOf(area.getDonationAmt()));
        }
        return areaList;
    }
}
