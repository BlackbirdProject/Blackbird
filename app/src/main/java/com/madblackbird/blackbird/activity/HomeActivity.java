package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.fragment.FavouriteDestinationFragment;
import com.madblackbird.blackbird.fragment.HomeFragment;
import com.madblackbird.blackbird.fragment.TripItinerariesFragment;

public class HomeActivity extends AppCompatActivity {

    public final static int PLACE_ACTIVITY = 51;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initializeUI();
    }

    private void initializeUI() {
        checkLoggedIn();
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(R.id.content_frame, new HomeFragment(), "homeFragment");
                    navigationView.getMenu().getItem(0).setChecked(true);
                    break;
                case R.id.menu_trip_history:
                case R.id.menu_favourite_destinations:
                    openLoginRequiredFragment(menuItem.getItemId());
                    break;
                case R.id.menu_log_out:
                    logOut();
                    break;
            }
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HomeFragment(), "homeFragment");
        navigationView.getMenu().getItem(0).setChecked(true);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openLoginRequiredFragment(int itemId) {
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (itemId) {
                case R.id.menu_trip_history:
                    transaction.replace(R.id.content_frame, new TripItinerariesFragment(), "tripItinerariesFragment");
                    navigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case R.id.menu_favourite_destinations:
                    transaction.replace(R.id.content_frame, new FavouriteDestinationFragment(), "favouriteDestinationsFragment");
                    navigationView.getMenu().getItem(2).setChecked(true);
                    break;
            }
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void checkLoggedIn() {
        if (firebaseUser == null) {
            navigationView.getMenu().getItem(3).setVisible(false);
        } else {
            navigationView.getMenu().getItem(3).setVisible(true);
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firebase_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                task -> {
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                });
    }

    @Override
    public void onBackPressed() {

    }
}
