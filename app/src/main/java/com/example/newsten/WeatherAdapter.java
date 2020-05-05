package com.example.newsten;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ForecastViewHolder> {

    private static final String BASE_ICON_URL = "https://www.weatherbit.io/static/img/icons/";
    private Context fCtx;
    private List<Weather> weathers;

    public WeatherAdapter(Context fCtx, List<Weather> weathers) {
        this.fCtx = fCtx;
        this.weathers = weathers;
    }


    public static class ForecastViewHolder extends RecyclerView.ViewHolder{
        ImageView forecastIconIv;
        TextView forecastTitleTv;
        TextView forecastMaxDegreeTv;
        TextView forecastDayTv;
        CardView cardView;


        ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            forecastIconIv = itemView.findViewById(R.id.ivIcon);
            forecastTitleTv = itemView.findViewById(R.id.tvLocation);
            forecastMaxDegreeTv = itemView.findViewById(R.id.tvAvdDeg);
            forecastDayTv = itemView.findViewById(R.id.tvDay);
            cardView = itemView.findViewById(R.id.card_view_weather);
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_items, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Weather weather = weathers.get(position);
        String str = BASE_ICON_URL + weather.getForecastIcon() + ".png";
        Glide.with(fCtx).load(str).apply(new RequestOptions().override(150,180)).into(holder.forecastIconIv);
        holder.forecastTitleTv.setText(weather.getForecastTitle());
        str = weather.getAvgTemp() + "°";

        holder.forecastMaxDegreeTv.setText(str);


        holder.forecastDayTv.setText(weather.getDayOfTheWeek());
    }

    @Override
    public int getItemCount() {

        return weathers.size();
    }


}
