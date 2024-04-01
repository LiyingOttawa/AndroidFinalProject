package algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Adapters;


// SunriseSunsetAdapter.java

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

public class SunriseSunsetAdapter extends RecyclerView.Adapter<SunriseSunsetAdapter.ViewHolder> {

    private List<SunriseSunsetData> dataList;
    private Context context;

    public SunriseSunsetAdapter(List<SunriseSunsetData> dataList) {
        this.dataList = dataList;
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
        SunriseSunsetData data = dataList.get(position);
        holder.sunriseTextView.setText(data.getSunrise());
        holder.sunsetTextView.setText(data.getSunset());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sunriseTextView;
        TextView sunsetTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sunriseTextView = itemView.findViewById(R.id.sunriseTextView);
            sunsetTextView = itemView.findViewById(R.id.sunsetTextView);
        }
    }
}
