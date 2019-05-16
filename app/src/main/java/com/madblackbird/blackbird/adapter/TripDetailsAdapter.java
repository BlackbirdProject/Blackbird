package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.dataClasses.Leg;

import java.util.List;

public class TripDetailsAdapter extends RecyclerView.Adapter<TripDetailsAdapter.Holder> {

    private List<Leg> legs;

    public TripDetailsAdapter(List<Leg> legs) {
        this.legs = legs;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_trip_detail, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(legs.get(position));
    }

    public Leg getLeg(int position) {
        return legs.get(position);
    }

    @Override
    public int getItemCount() {
        return legs.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final TextView lblTripDetail;

        private final Context context;

        Holder(View view) {
            super(view);
            lblTripDetail = view.findViewById(R.id.trip_detail_text_view);
            context = view.getContext();
        }

        void bind(Leg leg) {
            String text = "";
            if (leg.getHeadsign() != null)
                text += leg.getHeadsign();
            if (leg.getRouteShortName() != null)
                text += " " + leg.getRouteShortName();
            lblTripDetail.setText(text);
        }

    }

}
