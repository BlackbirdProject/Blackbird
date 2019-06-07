package com.madblackbird.blackbird.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.FavouriteDestinationAdapter;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.OTPTime;
import com.madblackbird.blackbird.service.TripDatabaseService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteDestinationFragment extends Fragment {

    @BindView(R.id.recycler_view_favourites)
    RecyclerView recyclerViewFavourites;
    @BindView(R.id.img_favourites_menu)
    ImageView imgOpenMenu;

    private FavouriteDestinationAdapter favouriteDestinationAdapter;
    private TripDatabaseService tripDatabaseService;
    private List<OTPPlace> places;

    public FavouriteDestinationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_destination, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
        tripDatabaseService = new TripDatabaseService();
        recyclerViewFavourites.setLayoutManager(new LinearLayoutManager(getContext()));
        places = new ArrayList<>();
        favouriteDestinationAdapter = new FavouriteDestinationAdapter(places);
        // TODO: Open home activity and set destination
        favouriteDestinationAdapter.setOnClickListener((v, position) -> {
            TripItinerariesFragment tripItinerariesFragment = new TripItinerariesFragment(null, places.get(position), new OTPTime());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, tripItinerariesFragment, "tripItinerariesFragment")
                    .addToBackStack(null)
                    .commit();
        });
        recyclerViewFavourites.setAdapter(favouriteDestinationAdapter);
        tripDatabaseService.getFavourites(this::addFavourite);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        imgOpenMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }

    private void addFavourite(OTPPlace place) {
        places.add(place);
        favouriteDestinationAdapter.notifyDataSetChanged();
    }

}
