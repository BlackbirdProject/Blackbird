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
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.PriceEstimate;

import java.util.List;

public class ItineraryRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_ITINERARY = 0;
    private static final int VIEW_TYPE_PRICE_ESTIMATES = 1;

    private List<Object> itineraries;
    private View.OnClickListener onClickListener;

    public ItineraryRecyclerViewAdapter(List<Object> itineraries) {
        this.itineraries = itineraries;
    }

    @Override
    public int getItemViewType(int position) {
        Object itinerary = itineraries.get(position);
        if (itinerary instanceof Itinerary) {
            return VIEW_TYPE_ITINERARY;
        } else if (itinerary instanceof PriceEstimate) {
            return VIEW_TYPE_PRICE_ESTIMATES;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_ITINERARY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_itinerary, parent, false);
            if (onClickListener != null)
                view.setOnClickListener(v -> onClickListener.onClick(v));
            return new ItineraryHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_price_estimate, parent, false);
            return new PriceEstimateHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object itinerary = itineraries.get(position);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_ITINERARY:
                ((ItineraryHolder) viewHolder).bind((Itinerary) itinerary);
                break;
            case VIEW_TYPE_PRICE_ESTIMATES:
                ((PriceEstimateHolder) viewHolder).bind((PriceEstimate) itinerary);
        }
    }

    public void setOnClickListener(View.OnClickListener callback) {
        onClickListener = callback;
    }

    public Object getItinerary(int position) {
        return itineraries.get(position);
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    static class ItineraryHolder extends RecyclerView.ViewHolder {

        private final LinearLayout layoutTransportIcons, layoutTransportSummary;
        private final TextView lblDuration, lblTime;

        private final Context context;

        ItineraryHolder(View view) {
            super(view);
            layoutTransportIcons = view.findViewById(R.id.linear_transport_icons);
            layoutTransportSummary = view.findViewById(R.id.vertical_transport_summary);
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
                View view = LayoutInflater.from(context).inflate(R.layout.item_itinerary_leg, null);
                ImageView imgRouteType = view.findViewById(R.id.item_leg_icon);
                TextView lblRouteName = view.findViewById(R.id.item_leg_name);
                Drawable drawable;
                switch (leg.getMode()) {
                    case "BUS":
                        drawable = context.getDrawable(R.drawable.ic_directions_bus);
                        break;
                    case "RAIL":
                        drawable = context.getDrawable(R.drawable.ic_train);
                        break;
                    case "SUBWAY":
                        drawable = context.getDrawable(R.drawable.ic_metro_madrid);
                        break;
                    default:
                        drawable = context.getDrawable(R.drawable.ic_directions_walk);
                }
                GradientDrawable lblRouteNameBackground = (GradientDrawable) lblRouteName.getBackground();
                lblRouteName.setText(leg.getRouteShortName());
                if (leg.getRouteColor() != null && !leg.getRouteColor().equals(""))
                    lblRouteNameBackground.setColor(Color.parseColor("#" + leg.getRouteColor()));
                imgRouteType.setImageDrawable(drawable);
                if (leg.getRouteShortName() == null || leg.getRouteShortName().equals("")) {
                    lblRouteName.setBackgroundColor(Color.TRANSPARENT);
                    lblRouteName.setTextColor(Color.BLACK);
                    lblRouteName.setText(formatDistance(leg.getDistance().intValue()));
                }
                ImageView imgIcon = new ImageView(context);
                imgIcon.setImageDrawable(drawable);
                layoutTransportIcons.addView(imgIcon);
                layoutTransportSummary.addView(view);
            }
        }

        private String formatDuration(Integer duration) {
            return duration / 60 + context.getString(R.string.space_minutes);
        }

        private String formatDistance(Integer distance) {
            return distance >= 1000 ? distance / 1000 + " km" : distance + " m";
        }

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
