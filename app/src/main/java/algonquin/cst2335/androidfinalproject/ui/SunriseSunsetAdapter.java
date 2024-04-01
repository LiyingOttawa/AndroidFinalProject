package algonquin.cst2335.androidfinalproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Dao.FavoriteLocation;

public class SunriseSunsetAdapter extends RecyclerView.Adapter<SunriseSunsetAdapter.LocationViewHolder> {

    private final List<FavoriteLocation> favoriteLocations;
    private int selectedPosition;

    public SunriseSunsetAdapter(List<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latitude_longitude, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        FavoriteLocation location = favoriteLocations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return favoriteLocations.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }


    static class LocationViewHolder extends RecyclerView.ViewHolder {

        private TextView latitudeTextView;
        private TextView longitudeTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            latitudeTextView = itemView.findViewById(R.id.latitudeEditText);
            longitudeTextView = itemView.findViewById(R.id.longitudeEditText);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                // Add your implementation here
            });
        }

        public void bind(FavoriteLocation location) {
            latitudeTextView.setText("Latitude: " + location.getLatitude());
            longitudeTextView.setText("Longitude: " + location.getLongitude());
        }
    }
}
