package com.farms.adhanapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.farms.adhanapp.R;
import com.farms.adhanapp.model.Pray;
import com.farms.adhanapp.view.AdhanActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends ArrayAdapter<Pray> {

    private List<Pray> dataList;

    private Context context;
    public ListAdapter(Context context, List<Pray> dataList) {
        super(context, R.layout.layout_custom_list_view_7days_pray, dataList);
        this.context=context;
        this.dataList = dataList;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the custom layout for each list item
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_custom_list_view_7days_pray, parent, false);

            // Create a ViewHolder and store references to the views in the custom layout
            viewHolder = new ViewHolder();
            viewHolder.textViewPrayerName=convertView.findViewById(R.id.custom_list_7day_pray_text_view_time_pray);
            viewHolder.textViewAdhanTime=convertView.findViewById(R.id.custom_list_7day_pray_text_view_date_pray);
            convertView.setTag(viewHolder);
        } else {
            // Recycle the ViewHolder to use the existing views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        Pray pray = dataList.get(position);



        // Assuming pray.getTimeAdhan() returns a LocalDate object
        HijrahDate date = HijrahDate.from(pray.getDateTimePray());

// Define a formatter for Arabic locale
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", new Locale("ar"));

// Format the date using the formatter
        String formattedDate = date.format(formatter);

// Set the formatted date to your TextView
       viewHolder.textViewAdhanTime.setText(formattedDate);

        viewHolder.textViewPrayerName.setText(pray.getDateTimePray().format(DateTimeFormatter.ofPattern("hh:mm a", new Locale("ar"))));
        return convertView;
    }

    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView textViewPrayerName;
        TextView textViewAdhanTime;
    }


}
