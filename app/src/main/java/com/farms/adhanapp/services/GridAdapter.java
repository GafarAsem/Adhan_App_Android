package com.farms.adhanapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.batoulapps.adhan2.Prayer;
import com.farms.adhanapp.R;
import com.farms.adhanapp.controller.PraysController;
import com.farms.adhanapp.model.Pray;
import com.farms.adhanapp.view.AdhanActivity;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter<Pray> {

    private final List<Pray> dataList;
    private final Context context;
    public GridAdapter(Context context, List<Pray> dataList) {
        super(context, R.layout.layout_custom_card_grid_view, dataList);
        this.context=context;
        this.dataList = dataList;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the custom layout for each list item
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_custom_card_grid_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewPrayerName = convertView.findViewById(R.id.custom_card_text_view_name_pray);
            viewHolder.textViewAdhanTime = convertView.findViewById(R.id.custom_card_text_view_time_pray);
            viewHolder.iconImageView = convertView.findViewById(R.id.custom_card_image_view_icon_pray);
            convertView.setTag(viewHolder);
        } else {
            // Recycle the ViewHolder to use the existing views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        Pray pray = dataList.get(position);
        // Set the prayer name and Adhan time in the TextViews
        viewHolder.textViewPrayerName.setText(pray.getArabicNamePray());
        viewHolder.textViewAdhanTime.setText(pray.getDateTimePray().format(DateTimeFormatter.ofPattern("hh:mm a", new Locale("ar"))));

        if(PraysController.getNextPray(LocalDateTime.now()).getPrayer()==(pray.getPrayer()))
        {
            viewHolder.textViewAdhanTime.setTextColor(Color.rgb(255, 0, 0));
            viewHolder.textViewPrayerName.setTextColor(Color.rgb(255, 0, 0));
            ((AdhanActivity)context).setReminderText();
        }
        switch (pray.getPrayer()){
            case FAJR:
                viewHolder.iconImageView.setImageResource(R.drawable.sunrise);
                break;
            case DHUHR:
                viewHolder.iconImageView.setImageResource(R.drawable.sun);
                break;
            case ASR:
                viewHolder.iconImageView.setImageResource(R.drawable.sunrise);
                break;
            case MAGHRIB:
                viewHolder.iconImageView.setImageResource(R.drawable.moon);
                break;
            case ISHA:
                viewHolder.iconImageView.setImageResource(R.drawable.moon);
                break;

        }

        return convertView;
    }


    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView textViewPrayerName;
        TextView textViewAdhanTime;
        ImageView iconImageView;
    }


}
