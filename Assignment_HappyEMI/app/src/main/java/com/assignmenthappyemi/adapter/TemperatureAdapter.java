package com.assignmenthappyemi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignmenthappyemi.R;
import com.assignmenthappyemi.model.Temperature;
import com.assignmenthappyemi.utils.AppUtils;

import java.util.List;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder> {

    Activity context;
    List<Temperature.ForecastDay> forecastDayList;

    public TemperatureAdapter(Activity context, List<Temperature.ForecastDay> forecastDayList) {
        super();
        this.context = context;
        this.forecastDayList = forecastDayList;
    }

    @Override
    public TemperatureAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.temperature_list, viewGroup, false);
        TemperatureAdapter.ViewHolder viewHolder = new TemperatureAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TemperatureAdapter.ViewHolder viewHolder, final int position) {
        Temperature.ForecastDay forecastDay = forecastDayList.get(position);
        viewHolder.textViewForecastTemperature.setText(String.valueOf(forecastDay.getDay().getAvgtempC()) + context.getString(R.string.degree));
//        String date = AppUtils.epoch2DateString(forecastDay.getDateEpoch(), "EEEE, yyyy-dd-MMM");
        String date = AppUtils.epoch2DateString(forecastDay.getDateEpoch(), "EEEE");

        viewHolder.textViewForecastDay.setText(date);

    }


    @Override
    public int getItemCount() {
        return forecastDayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewForecastDay;
        TextView textViewForecastTemperature;


        public ViewHolder(View itemView) {
            super(itemView);


            textViewForecastDay = (TextView) itemView.findViewById(R.id.textview_forecast_day);
            textViewForecastTemperature = (TextView) itemView.findViewById(R.id.textview_forecast_temperature);

        }
    }

}
