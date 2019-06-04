package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.callback.PlaceClickListener;
import com.madblackbird.blackbird.dataClasses.OTPPlace;

import java.util.List;

public class FavouriteDestinationAdapter extends RecyclerView.Adapter<FavouriteDestinationAdapter.Holder> {

    private List<OTPPlace> places;
    private PlaceClickListener onClickListener;

    public FavouriteDestinationAdapter(List<OTPPlace> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favourite_place, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(places.get(position));
        if (onClickListener != null)
            holder.itemView.setOnClickListener(v -> onClickListener.onClick(v, position));
    }

    public void setOnClickListener(PlaceClickListener callback) {
        onClickListener = callback;
    }

    public OTPPlace getPlace(int position) {
        return places.get(position);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView lblName;

        private final Context context;

        Holder(View view) {
            super(view);
            lblName = view.findViewById(R.id.txt_favourite_place_name);
            context = view.getContext();
        }

        void bind(OTPPlace place) {
            lblName.setText(place.getName());
        }

    }

}