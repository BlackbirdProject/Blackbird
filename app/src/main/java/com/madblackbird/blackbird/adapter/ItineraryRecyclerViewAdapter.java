package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;

import java.util.List;

public class ItineraryRecyclerViewAdapter extends RecyclerView.Adapter<ItineraryRecyclerViewAdapter.Holder> {

    private List<Itinerary> itineraries;

    public ItineraryRecyclerViewAdapter(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_itinerary, null, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itineraries.get(position));
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final LinearLayout layoutTransportIcons;
        private final TextView lblTime;

        private final Context context;

        Holder(View view) {
            super(view);
            layoutTransportIcons = view.findViewById(R.id.linear_transport_icons);
            lblTime = view.findViewById(R.id.item_itinerary_time);
            context = view.getContext();
        }

        void bind(Itinerary itinerary) {
            lblTime.setText(formatTime(itinerary.getDuration()));
            for (Leg leg : itinerary.getLegs()) {
                Drawable drawable;
                switch (leg.getMode()) {
                    case "BUS":
                        drawable = context.getDrawable(R.drawable.ic_directions_bus);
                        break;
                    case "RAIL":
                        drawable = context.getDrawable(R.drawable.ic_train);
                        break;
                    case "SUBWAY":
                        drawable = context.getDrawable(R.drawable.ic_directions_subway);
                        break;
                    default:
                        drawable = context.getDrawable(R.drawable.ic_directions_walk);
                }
                TextView textView = new TextView(context);
                textView.setText(leg.getRouteShortName());
                if (leg.getRouteColor() != null && !leg.getRouteColor().equals(""))
                    textView.setTextColor(Color.parseColor("#" + leg.getRouteColor()));
                ImageView imageView = new ImageView(context);
                imageView.setImageDrawable(drawable);
                layoutTransportIcons.addView(textView);
            }
        }

        private String formatTime(Integer duration) {
            return duration / 60 + " minutes";
        }

    }

}
