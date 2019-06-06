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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.madblackbird.blackbird.callback.DistanceLoadCallback;
import com.madblackbird.blackbird.callback.FavouriteAddedCallback;
import com.madblackbird.blackbird.callback.OnItineraryLoadCallback;
import com.madblackbird.blackbird.callback.OnPlaceLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.OTPPlace;

public class TripDatabaseService {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    public TripDatabaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void addTrip(Itinerary itinerary) {
        itinerary.setExpanded(false);
        if (firebaseUser != null) {
            DatabaseReference savedTrips = firebaseDatabase.getReference("savedTrips");
            savedTrips.child(firebaseUser.getUid())
                    .push()
                    .setValue(itinerary);
        }
        addDistance(itinerary);
    }

    private void addDistance(Itinerary itinerary) {
        if (firebaseUser != null) {
            double distance = 0.0;
            for (Leg leg : itinerary.getLegs()) {
                if (leg.getDistance() != null)
                    distance += leg.getDistance();
            }
            final double totalDistance = distance;
            DatabaseReference distanceRef = firebaseDatabase.getReference("distance");
            distanceRef.child(firebaseUser.getUid()).runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                    try {
                        double unreadNum = mutableData.getValue(Double.class);
                        double transactionDistance = totalDistance + unreadNum;
                        mutableData.setValue(transactionDistance);
                    } catch (NullPointerException e) {
                        mutableData.setValue(totalDistance);
                    }
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                }
            });
        }
    }

    public void addFavourite(OTPPlace place, FavouriteAddedCallback callback) {
        if (firebaseUser != null) {
            DatabaseReference favouritePlaces = firebaseDatabase.getReference("favouritePlaces");
            favouritePlaces.child(firebaseUser.getUid())
                    .push()
                    .setValue(place)
                    .addOnSuccessListener(aVoid -> {
                        callback.favouriteAdded();
                    });
        }
    }

    public void getDistance(DistanceLoadCallback callback) {
        if (firebaseUser != null) {
            DatabaseReference distance = firebaseDatabase.getReference("distance");
            distance.child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                callback.onDistanceLoad(dataSnapshot.getValue(Double.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void getFavourites(OnPlaceLoadCallback callback) {
        if (firebaseUser != null) {
            DatabaseReference favouritePlaces = firebaseDatabase.getReference("favouritePlaces");
            favouritePlaces.child(firebaseUser.getUid())
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            OTPPlace place = dataSnapshot.getValue(OTPPlace.class);
                            if (place != null)
                                callback.onLoad(place);
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

    public void getItineraries(OnItineraryLoadCallback callback) {
        if (firebaseUser != null) {
            DatabaseReference savedTrips = firebaseDatabase.getReference("savedTrips");
            savedTrips.child(firebaseUser.getUid())
                    .addChildEventListener(new ChildEventListener() {
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
