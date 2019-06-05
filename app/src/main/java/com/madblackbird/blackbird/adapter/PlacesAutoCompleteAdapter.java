package com.madblackbird.blackbird.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.madblackbird.blackbird.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PlacesAutoCompleteAdapter extends RecyclerView.Adapter<PlacesAutoCompleteAdapter.PredictionHolder> implements Filterable {

    private static final String TAG = "PlacesAutoAdapter";
    private ArrayList<PlaceAutocomplete> mResultList = new ArrayList<>();

    private Context mContext;
    private CharacterStyle STYLE_BOLD;
    private CharacterStyle STYLE_NORMAL;
    private final PlacesClient placesClient;
    private ClickListener clickListener;

    public PlacesAutoCompleteAdapter(Context context) {
        mContext = context;
        STYLE_BOLD = new StyleSpan(Typeface.BOLD);
        STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);
        placesClient = com.google.android.libraries.places.api.Places.createClient(context);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void click(Place place);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    mResultList = getPredictions(constraint);
                    if (mResultList != null) {
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0)
                    notifyDataSetChanged();
            }
        };
    }


    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {
        final ArrayList<PlaceAutocomplete> resultList = new ArrayList<>();
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationRestriction(RectangularBounds.newInstance(
                        new LatLng(39.938037, -4.815362),
                        new LatLng(41.211806, -2.870480)))
                .setSessionToken(token)
                .setQuery(constraint.toString())
                .build();

        Task<FindAutocompletePredictionsResponse> autocompletePredictions = placesClient.findAutocompletePredictions(request);

        try {
            Tasks.await(autocompletePredictions, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (autocompletePredictions.isSuccessful()) {
            FindAutocompletePredictionsResponse findAutocompletePredictionsResponse = autocompletePredictions.getResult();
            if (findAutocompletePredictionsResponse != null)
                for (AutocompletePrediction prediction : findAutocompletePredictionsResponse.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPlaceId());
                    resultList.add(new PlaceAutocomplete(
                            prediction.getPlaceId(),
                            prediction.getPrimaryText(STYLE_NORMAL).toString(),
                            prediction.getFullText(STYLE_BOLD).toString(),
                            prediction.getPlaceTypes()));
                }

            return resultList;
        } else {
            return resultList;
        }

    }

    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.item_place_search, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        PlaceAutocomplete placeAutocomplete = mResultList.get(i);
        mPredictionHolder.address.setText(placeAutocomplete.address);
        mPredictionHolder.imgPlaceImage.setImageDrawable(mContext.getDrawable(getPlaceTypeDrawable(placeAutocomplete.types)));
    }

    private int getPlaceTypeDrawable(List<Place.Type> types) {
        if (containsType(types, "SUBWAY_STATION")) {
            return R.drawable.ic_metro_madrid;
        } else if (containsType(types, "TRANSIT_STATION") || containsType(types, "TRAIN_STATION")) {
            return R.drawable.ic_train;
        } else if (containsType(types, "BUS_STATION")) {
            return R.drawable.ic_directions_bus;
        }
        return R.drawable.ic_location_marker;
    }

    private boolean containsType(final List<Place.Type> list, final String type) {
        for (Place.Type t : list) {
            if (t.name().equals(type))
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address;
        private RelativeLayout mRow;
        private ImageView imgPlaceImage;

        PredictionHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.item_place_address);
            imgPlaceImage = itemView.findViewById(R.id.item_place_image);
            mRow = itemView.findViewById(R.id.predictedRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PlaceAutocomplete item = mResultList.get(getAdapterPosition());
            if (v.getId() == R.id.predictedRow) {
                String placeId = String.valueOf(item.placeId);
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(request).addOnSuccessListener(response -> {
                    Place place = response.getPlace();
                    clickListener.click(place);
                }).addOnFailureListener(exception -> {
                    if (exception instanceof ApiException) {
                        Toast.makeText(mContext, exception.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public class PlaceAutocomplete {

        CharSequence placeId, address, area;
        List<Place.Type> types;

        PlaceAutocomplete(CharSequence placeId, CharSequence area, CharSequence address, List<Place.Type> types) {
            this.placeId = placeId;
            this.area = area;
            this.address = address;
            this.types = types;
        }

        @NotNull
        @Override
        public String toString() {
            return area.toString();
        }

    }

}
