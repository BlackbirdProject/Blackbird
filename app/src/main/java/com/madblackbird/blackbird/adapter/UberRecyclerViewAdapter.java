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

import java.util.List;

public class UberRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<PriceEstimate> priceEstimates;
    private View.OnClickListener itinerariesClickListener;

    public UberRecyclerViewAdapter(List<PriceEstimate> priceEstimates) {
        this.priceEstimates = priceEstimates;
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
        PriceEstimate itinerary = priceEstimates.get(position);
        ((PriceEstimateHolder) viewHolder).bind(itinerary);
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

        private final TextView lblUberType, lblEstimate;

        private final Context context;

        PriceEstimateHolder(View view) {
            super(view);
            lblUberType = view.findViewById(R.id.lbl_uber_type);
            lblEstimate = view.findViewById(R.id.lbl_trip_price);
            context = view.getContext();
        }

        void bind(PriceEstimate priceEstimate) {
            lblUberType.setText(priceEstimate.getLocalizedisplayName());
            lblEstimate.setText(priceEstimate.getEstimate());
        }

    }

}
