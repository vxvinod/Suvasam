package com.example.suvasam.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.suvasam.model.Awareness;
import com.example.suvasam.model.Events;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AwarenessFirebase {

    public AwarenessFirebase() {
    }

    public static ArrayList<Awareness> fetchDataFromFirebase() {
        final ArrayList<Awareness> awarenessesList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("awareness").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count", ""+dataSnapshot.getChildrenCount());
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Awareness awareness = postSnapshot.getValue(Awareness.class);
                    awarenessesList.add(awareness);
                    Log.e("Get Data", awareness.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return awarenessesList;
    }
}
