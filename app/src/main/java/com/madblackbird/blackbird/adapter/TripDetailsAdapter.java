package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.Stop;

import java.util.List;

public class TripDetailsAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_LEG = 0;
    private static final int VIEW_TYPE_PLACE = 1;

    private List<Object> tripDetails;

    public TripDetailsAdapter(List<Object> tripDetails) {
        this.tripDetails = tripDetails;
    }

    @Override
    public int getItemViewType(int position) {
        Object tripDetail = tripDetails.get(position);
        if (tripDetail instanceof Leg) {
            return VIEW_TYPE_LEG;
        } else if (tripDetail instanceof OTPPlace) {
            return VIEW_TYPE_PLACE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_LEG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trip_detail, parent, false);
            return new LegHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trip_detail_place, parent, false);
            return new PlaceHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object tripDetail = tripDetails.get(position);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_LEG:
                LegHolder legHolder = (LegHolder) viewHolder;
                Leg leg = (Leg) tripDetail;
                legHolder.bind(leg);
                legHolder.mainLayout.setOnClickListener(v -> {
                    boolean expanded = leg.isExpanded();
                    leg.setExpanded(!expanded);
                    notifyItemChanged(position);
                });
                break;
            case VIEW_TYPE_PLACE:
                ((PlaceHolder) viewHolder).bind((OTPPlace) tripDetail);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tripDetails.size();
    }

    static class LegHolder extends RecyclerView.ViewHolder {

        private final ImageView tripDetailImage, imgExpand;
        private final TextView lblLine, lblDuration, lblStopNumber;
        private final LinearLayout layoutStopList, mainLayout;

        private final Context context;

        LegHolder(View view) {
            super(view);
            tripDetailImage = view.findViewById(R.id.trip_detail_image);
            lblLine = view.findViewById(R.id.trip_detail_line);
            lblDuration = view.findViewById(R.id.trip_detail_text_view);
            lblStopNumber = view.findViewById(R.id.detail_leg_stops);
            layoutStopList = view.findViewById(R.id.layout_stop_list);
            mainLayout = view.findViewById(R.id.trip_detail_main_layout);
            imgExpand = view.findViewById(R.id.trip_detail_img_expand);
            context = view.getContext();
        }

        void bind(Leg leg) {
            boolean expanded = leg.isExpanded();
            layoutStopList.setVisibility(expanded ? View.VISIBLE : View.GONE);
            String text = "";
            if (leg.getHeadsign() != null)
                text += leg.getHeadsign();
            if (leg.getRouteShortName() != null)
                text += " " + leg.getRouteShortName();
            lblDuration.setText(text);
            lblDuration.setText(formatDuration(leg.getDuration()));
            if (leg.getIntermediateStops() != null) {
                int stopNumber = leg.getIntermediateStops().size();
                if (stopNumber > 0) {
                    lblStopNumber.setText(context.getString(R.string.stops, stopNumber));
                    imgExpand.setImageDrawable(
                            context.getDrawable(
                                    expanded ? R.drawable.ic_expand_up : R.drawable.ic_expand_down));
                } else {
                    mainLayout.setOnClickListener(null);
                }
            }
            layoutStopList.removeAllViews();
            if (leg.getIntermediateStops() != null)
                for (Stop stop : leg.getIntermediateStops()) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_intermediate_stop, layoutStopList, false);
                    TextView lblStopName = view.findViewById(R.id.lbl_stop_name);
                    lblStopName.setText(stop.getName());
                    layoutStopList.addView(view);
                }
            Drawable drawable;
            switch (leg.getMode()) {
                case "BUS":
                    drawable = context.getDrawable(R.drawable.ic_directions_bus);
                    break;
                case "RAIL":
                    drawable = context.getDrawable(R.drawable.ic_train);
                    break;
                case "TRAM":
                    drawable = context.getDrawable(R.drawable.ic_metro_ligero);
                    break;
                case "SUBWAY":
                    drawable = context.getDrawable(R.drawable.ic_metro_madrid);
                    break;
                default:
                    drawable = context.getDrawable(R.drawable.ic_directions_walk);
            }
            Drawable lblRouteNameBackground = lblLine.getBackground();
            lblLine.setText(leg.getRouteShortName());
            if (leg.getRouteColor() != null && !leg.getRouteColor().equals("")) {
                int color = Color.parseColor("#" + leg.getRouteColor());
                if (Color.parseColor("#ffffff") == color)
                    color = Color.BLUE;
                lblRouteNameBackground.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
            tripDetailImage.setImageDrawable(drawable);
            if (leg.getRouteShortName() == null || leg.getRouteShortName().equals("")) {
                lblLine.setBackgroundColor(Color.TRANSPARENT);
                lblLine.setTextColor(Color.BLACK);
                lblLine.setText(formatDistance(leg.getDistance().intValue()));
            }
        }

        private String formatDuration(Integer duration) {
            return duration / 60 + context.getString(R.string.space_minutes);
        }

        private String formatDistance(Integer distance) {
            return distance >= 1000 ? distance / 1000 + " km" : distance + " m";
        }

    }

    static class PlaceHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final TextView lblPlaceName;

        PlaceHolder(View view) {
            super(view);
            context = view.getContext();
            lblPlaceName = view.findViewById(R.id.trip_detail_place_name);
        }

        void bind(OTPPlace place) {
            lblPlaceName.setText(place.getName());
        }

    }

}
