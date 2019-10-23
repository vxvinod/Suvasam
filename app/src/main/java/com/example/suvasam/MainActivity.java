package com.example.suvasam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.suvasam.adapter.CustomViewPageAdapter;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    ViewPager viewPager;
    Fragment fragment1 = new DonateFragment();
    Fragment fragment2 = new EventsFragment();
    Fragment fragment3 = new AwarenessFragment();
    FragmentManager fm ;
    Map<String, Fragment.SavedState> savedStateMap = new HashMap<>();
    Fragment active ;
    private InterstitialAd mInterstitialAd;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.e("MAIN ACTIVITY", "NAV HOME SELECTED");
                    getSupportActionBar().show();
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    getSupportActionBar().show();
                    Log.e("MAIN ACTIVITY", "NAV DASHBOARD SELECTED");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    getSupportActionBar().hide();
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MAIN ACTIVITY", "Inside if OnCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setTitle("Suvasam");
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-6506911315414653/6509638653");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }

        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        CustomViewPageAdapter adapter = new CustomViewPageAdapter(MainActivity.this.getSupportFragmentManager());
        adapter.addFragment(new DonateFragment(), "donate");
        adapter.addFragment(new EventsFragment(), "event");

        adapter.addFragment(new AwarenessFragment(), "awareness");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem prevMenuItem = null;
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
