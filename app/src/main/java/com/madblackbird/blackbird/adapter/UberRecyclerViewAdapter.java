package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.dataClasses.PriceEstimate;
import com.madblackbird.blackbird.dataClasses.TimeEstimate;

import java.util.HashMap;
import java.util.List;

public class UberRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<PriceEstimate> priceEstimates;
    private HashMap<String, TimeEstimate> timeEstimates;
    private View.OnClickListener itinerariesClickListener;

    public UberRecyclerViewAdapter(List<PriceEstimate> priceEstimates, HashMap<String, TimeEstimate> timeEstimates) {
        this.priceEstimates = priceEstimates;
        this.timeEstimates = timeEstimates;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_price_estimate, parent, false);
        if (itinerariesClickListener != null)
            view.setOnClickListener(v -> itinerariesClickListener.onClick(v));
        return new PriceEstimateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        PriceEstimate priceEstimate = priceEstimates.get(position);
        PriceEstimateHolder priceEstimateHolder = (PriceEstimateHolder) viewHolder;
        priceEstimateHolder.bind(priceEstimate, timeEstimates.get(priceEstimate.getProductid()));
    }

    public void setItinerariesClickListener(View.OnClickListener callback) {
        itinerariesClickListener = callback;
    }

    public PriceEstimate getPriceEstimate(int position) {
        return priceEstimates.get(position);
    }

    @Override
    public int getItemCount() {
        return priceEstimates.size();
    }

    static class PriceEstimateHolder extends RecyclerView.ViewHolder {

        private final TextView lblUberType, lblEstimate, lblDuration, lblTimeEstimate;

        private final Context context;

        PriceEstimateHolder(View view) {
            super(view);
            lblUberType = view.findViewById(R.id.lbl_uber_type);
            lblEstimate = view.findViewById(R.id.lbl_trip_price);
            lblDuration = view.findViewById(R.id.lbl_trip_duration);
            lblTimeEstimate = view.findViewById(R.id.lbl_time_estimate);
            context = view.getContext();
        }

        void bind(PriceEstimate priceEstimate, TimeEstimate timeEstimate) {
            lblUberType.setText(priceEstimate.getLocalizedisplayName());
            lblEstimate.setText(priceEstimate.getEstimate());
            lblDuration.setText(priceEstimate.getDuration() / 60 + " minutes");
            if (timeEstimate != null) {
                lblTimeEstimate.setText(timeEstimate.getEstimate() / 60 + " minute wait");
            }
        }

    }

}
