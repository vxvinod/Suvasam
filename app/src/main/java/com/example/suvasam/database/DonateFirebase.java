package com.example.suvasam.database;

import android.util.Log;

import com.example.suvasam.model.Awareness;
import com.example.suvasam.model.Donate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class DonateFirebase {

    public static ArrayList<Donate> fetchDataFromFirebase() {
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
