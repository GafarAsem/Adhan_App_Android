package com.farms.adhanapp.pray.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farms.adhanapp.R;
import com.farms.adhanapp.pray.helper.PraysHelper;
import com.farms.adhanapp.pray.model.PrayModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter<PrayModel> {

    private final List<PrayModel> dataList;
    private final Context context;
    public GridAdapter(Context context, List<PrayModel> dataList) {
        super(context, R.layout.grid_view_layout_custom_card, dataList);
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
            convertView = inflater.inflate(R.layout.grid_view_layout_custom_card, parent, false);
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
        PrayModel prayModel = dataList.get(position);
        // Set the prayer name and Adhan time in the TextViews
        viewHolder.textViewPrayerName.setText(prayModel.getArabicNamePray());
        viewHolder.textViewAdhanTime.setText(prayModel.getDateTimePray().format(DateTimeFormatter.ofPattern("h:mm a", new Locale("ar"))));

        if(PraysHelper.getNextPray()!=null)
            if(PraysHelper.getNextPray().getPrayer()==(prayModel.getPrayer()))
            {
                viewHolder.textViewAdhanTime.setTextColor(Color.rgb(255, 0, 0));
                viewHolder.textViewPrayerName.setTextColor(Color.rgb(255, 0, 0));
//                ((AdhanActivity)context).setReminderText();
            }
        switch (prayModel.getPrayer()){
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
