package com.madblackbird.blackbird.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.madblackbird.blackbird.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.activity_main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.account:
                    Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings:
                    Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mycart:
                    Toast.makeText(HomeActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return true;
            }
            return true;

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.account:
                MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
                if (mapFragment != null)
                    mapFragment.changeMapType();
                return true;
            /*case R.id.menu_map:
                MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
                if (mapFragment != null)
                    mapFragment.changeMapType();
                return true;
            case R.id.menu_filter:
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
                if (homeFragment != null)
                    homeFragment.changeFilter();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

}
