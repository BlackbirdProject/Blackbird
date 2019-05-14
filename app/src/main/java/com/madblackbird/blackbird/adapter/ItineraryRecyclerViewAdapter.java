package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
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
    private View.OnClickListener onClickListener;

    public ItineraryRecyclerViewAdapter(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_itinerary, viewGroup, false);
        RecyclerView.ViewHolder holder = new Holder(view);
        if (onClickListener != null)
            holder.itemView.setOnClickListener(v -> onClickListener.onClick(v));
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itineraries.get(position));
    }

    public void setOnClickListener(View.OnClickListener callback) {
        onClickListener = callback;
    }

    public Itinerary getItinerary(int position) {
        return itineraries.get(position);
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final LinearLayout layoutTransportIcons;
        private final TextView lblDuration, lblTime;

        private final Context context;

        Holder(View view) {
            super(view);
            layoutTransportIcons = view.findViewById(R.id.linear_transport_icons);
            lblDuration = view.findViewById(R.id.item_itinerary_duration);
            lblTime = view.findViewById(R.id.item_itinerary_time);
            context = view.getContext();
        }

        void bind(Itinerary itinerary) {
            lblDuration.setText(formatDuration(itinerary.getDuration()));
            String time = DateUtils.formatDateTime(context, itinerary.getStartTime(),
                    DateUtils.FORMAT_SHOW_TIME) + " > " + DateUtils.formatDateTime(context, itinerary.getEndTime(),
                    DateUtils.FORMAT_SHOW_TIME);
            lblTime.setText(time);
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
                layoutTransportIcons.addView(imageView);
                layoutTransportIcons.addView(textView);
            }
        }

        private String formatDuration(Integer duration) {
            return duration / 60 + " minutes";
        }

    }

}
