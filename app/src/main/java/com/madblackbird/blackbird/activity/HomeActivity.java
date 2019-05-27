package com.madblackbird.blackbird.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);
        initializeUI();
    }

    private void initializeUI() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(R.id.content_frame, new HomeFragment(), "homeFragment");
                    break;
                /*case R.id.nav_map:
                    menuItems.get(R.id.menu_filter).setVisible(false);
                    menuItems.get(R.id.menu_map).setVisible(true);
                    transaction.replace(R.id.content_frame, new MapFragment(), "mapFragment");*/
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_account:
                return false;
            /*case R.id.account:
                MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
                if (mapFragment != null)
                    mapFragment.changeMapType();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

}
