package com.madblackbird.blackbird.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.madblackbird.blackbird.dataClasses.Itinerary;

public class TripHistoryService {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    public TripHistoryService() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void addTrip(Itinerary itinerary) {
        firebaseFirestore.collection("savedTrips")
                .document(firebaseUser.getUid())
                .set(itinerary);
    }

}
