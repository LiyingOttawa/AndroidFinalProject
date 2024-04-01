package algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Model.SunriseSunsetData;

public class FavoriteLocationAdapter extends RecyclerView.Adapter<FavoriteLocationAdapter.ViewHolder> {

    private List<SunriseSunsetData> sunriseSunsetDataList;
    private Context context;
    private OnItemClickListener listener;

    public FavoriteLocationAdapter(List<SunriseSunsetData> sunriseSunsetDataList) {
        this.sunriseSunsetDataList = sunriseSunsetDataList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.activity_sunrise_sunset_lookup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SunriseSunsetData data = sunriseSunsetDataList.get(position);
        holder.dateTextView.setText(data.getDate());
        holder.sunriseTextView.setText(data.getSunrise());
        holder.sunsetTextView.setText(data.getSunset());
    }

    @Override
    public int getItemCount() {
        return sunriseSunsetDataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateTextView;
        TextView sunriseTextView;
        TextView sunsetTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            sunriseTextView = itemView.findViewById(R.id.sunriseTextView);
            sunsetTextView = itemView.findViewById(R.id.sunsetTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, position);
                }
            }
        }
    }
}
