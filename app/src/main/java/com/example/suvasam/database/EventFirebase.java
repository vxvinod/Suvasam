package com.example.suvasam.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.suvasam.model.Events;
import com.example.suvasam.widgets.SuvasamEventWidget;
import com.example.suvasam.widgets.WidgetUpdateIntentService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventFirebase {
    public EventFirebase() {
    }

    public static ArrayList<Events> fetchDataFromFirebase() {
        final ArrayList<Events> eventsList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count", ""+dataSnapshot.getChildrenCount());
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Events events = postSnapshot.getValue(Events.class);
                    eventsList.add(events);
                    Log.e("Get Data", events.name);
                }
                Log.e("SETTING WIDGET", "Setting Data to Widget");
                SuvasamEventWidget.mInterestedEvents = eventsList;
               // WidgetUpdateIntentService.startAddWidgetData(, eventsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return eventsList;
    }

    public static void updateFavInEvents(int eventId, boolean fav){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("events").child(String.valueOf(eventId)).child("fav"). setValue(fav).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }




}
