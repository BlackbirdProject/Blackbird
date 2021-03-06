package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import com.madblackbird.blackbird.callback.ItinerariesClickListener;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;

import java.util.LinkedHashSet;
import java.util.List;

public class ItineraryRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Itinerary> itineraries;
    private ItinerariesClickListener itinerariesClickListener;

    public ItineraryRecyclerViewAdapter(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_itinerary, parent, false);
        return new ItineraryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Itinerary itinerary = itineraries.get(position);
        ItineraryHolder itineraryHolder = (ItineraryHolder) viewHolder;
        itineraryHolder.bind(itinerary);
        itineraryHolder.itineraryParentView.setOnClickListener(v -> {
            for (Itinerary otherItinerary : itineraries) {
                if (otherItinerary.isExpanded() && !otherItinerary.equals(itinerary)) {
                    otherItinerary.setExpanded(false);
                    notifyItemChanged(itineraries.indexOf(otherItinerary));
                }
            }
            boolean expanded = itinerary.isExpanded();
            itinerary.setExpanded(!expanded);
            notifyItemChanged(position);
        });
        if (itinerariesClickListener != null)
            itineraryHolder.imgOpenTripDetails.setOnClickListener(v -> itinerariesClickListener.onClick(v, position));
    }

    public void setItinerariesClickListener(ItinerariesClickListener callback) {
        itinerariesClickListener = callback;
    }

    public Itinerary getItinerary(int position) {
        return itineraries.get(position);
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    static class ItineraryHolder extends RecyclerView.ViewHolder {

        private final LinearLayout layoutTransportIcons, layoutTransportSummary, itineraryParentView, verticalTransportIcons;
        private final TextView lblDuration, lblTime;
        private final ImageView imgOpenTripDetails;

        private final Context context;

        ItineraryHolder(View view) {
            super(view);
            layoutTransportIcons = view.findViewById(R.id.linear_transport_icons);
            verticalTransportIcons = view.findViewById(R.id.vertical_transport_icons);
            layoutTransportSummary = view.findViewById(R.id.vertical_transport_summary);
            itineraryParentView = view.findViewById(R.id.item_itinerary_parent_view);
            lblDuration = view.findViewById(R.id.item_itinerary_duration);
            lblTime = view.findViewById(R.id.item_itinerary_time);
            imgOpenTripDetails = view.findViewById(R.id.img_open_trip_details);
            context = view.getContext();
        }

        void bind(Itinerary itinerary) {
            boolean expanded = itinerary.isExpanded();
            layoutTransportSummary.setVisibility(expanded ? View.VISIBLE : View.GONE);
            lblDuration.setText(formatDuration(itinerary.getDuration()));
            String time = DateUtils.formatDateTime(context, itinerary.getStartTime(),
                    DateUtils.FORMAT_SHOW_TIME) + " - " + DateUtils.formatDateTime(context, itinerary.getEndTime(),
                    DateUtils.FORMAT_SHOW_TIME);
            lblTime.setText(time);
            LinkedHashSet<String> legModes = new LinkedHashSet<>();
            verticalTransportIcons.removeAllViews();
            layoutTransportIcons.removeAllViews();
            for (Leg leg : itinerary.getLegs()) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_itinerary_leg, verticalTransportIcons, false);
                ImageView imgRouteType = view.findViewById(R.id.item_leg_icon);
                TextView lblRouteName = view.findViewById(R.id.item_leg_name);
                TextView lblStopNumber = view.findViewById(R.id.item_leg_stops);
                if (leg.getIntermediateStops() != null) {
                    int stopNumber = leg.getIntermediateStops().size();
                    if (stopNumber > 0)
                        lblStopNumber.setText(context.getString(R.string.stops, stopNumber));
                }
                legModes.add(leg.getMode());
                GradientDrawable lblRouteNameBackground = (GradientDrawable) lblRouteName.getBackground();
                lblRouteName.setText(leg.getRouteShortName());
                if (leg.getRouteColor() != null && !leg.getRouteColor().equals("")) {
                    int color = Color.parseColor("#" + leg.getRouteColor());
                    if (Color.parseColor("#ffffff") == color)
                        color = Color.BLUE;
                    lblRouteNameBackground.setColor(color);
                }
                imgRouteType.setImageDrawable(getLegModeDrawable(leg.getMode()));
                if (leg.getRouteShortName() == null || leg.getRouteShortName().equals("")) {
                    lblRouteName.setBackgroundColor(Color.TRANSPARENT);
                    lblRouteName.setTextColor(Color.BLACK);
                    lblRouteName.setText(formatDistance(leg.getDistance().intValue()));
                }
                verticalTransportIcons.addView(view);
            }
            for (String legMode : legModes) {
                ImageView imgIcon = new ImageView(context);
                imgIcon.setImageDrawable(getLegModeDrawable(legMode));
                layoutTransportIcons.addView(imgIcon);
            }
        }


        private Drawable getLegModeDrawable(String legMode) {
            switch (legMode) {
                case "BUS":
                    return context.getDrawable(R.drawable.ic_directions_bus);
                case "RAIL":
                    return context.getDrawable(R.drawable.ic_cercanias_logo);
                case "TRAM":
                    return context.getDrawable(R.drawable.ic_metro_ligero);
                case "SUBWAY":
                    return context.getDrawable(R.drawable.ic_metro_madrid);
                default:
                    return context.getDrawable(R.drawable.ic_directions_walk);
            }
        }

        private String formatDuration(Integer duration) {
            return duration / 60 + context.getString(R.string.space_minutes);
        }

        private String formatDistance(Integer distance) {
            return distance >= 1000 ? distance / 1000 + " km" : distance + " m";
        }

    }

}
