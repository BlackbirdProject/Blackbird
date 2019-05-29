package com.madblackbird.blackbird.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madblackbird.blackbird.R;

public class FavouriteDestinationFragment extends Fragment {


    public FavouriteDestinationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_destination, container, false);
    }

}
