package com.madblackbird.blackbird.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madblackbird.blackbird.callback.OnItineraryLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;

public class TripHistoryService {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    public TripHistoryService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void addTrip(Itinerary itinerary) {
        if (firebaseUser != null) {
            DatabaseReference savedTrips = firebaseDatabase.getReference("savedTrips");
            savedTrips.child(firebaseUser.getUid())
                    .setValue(itinerary);
        }
    }

    public void getItineraries(OnItineraryLoadCallback callback) {
        if (firebaseUser != null) {
            DatabaseReference savedTrips = firebaseDatabase.getReference("savedTrips");
            savedTrips.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Itinerary itinerary = dataSnapshot.getValue(Itinerary.class);
                    if (itinerary != null)
                        callback.onLoad(itinerary);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
