package com.farms.adhanapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.batoulapps.adhan2.Prayer;
import com.farms.adhanapp.R;
import com.farms.adhanapp.model.Pray;
import com.farms.adhanapp.view.AdhanActivity;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Pray> {

    private List<Pray> dataList;
    private boolean nextPray=false;

    private Context context;
    public ListAdapter(Context context, List<Pray> dataList) {
        super(context, R.layout.layout_list_alarm_pray, dataList);
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
            convertView = inflater.inflate(R.layout.layout_list_alarm_pray, parent, false);

            // Create a ViewHolder and store references to the views in the custom layout
            viewHolder = new ViewHolder();
            viewHolder.textViewPrayerName = convertView.findViewById(R.id.list_name_pray);
            viewHolder.textViewAdhanTime = convertView.findViewById(R.id.list_time_pray);
            viewHolder.textViewReminder = convertView.findViewById(R.id.list_reminder_time);
            convertView.setTag(viewHolder);
        } else {
            // Recycle the ViewHolder to use the existing views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        Pray pray = dataList.get(position);

        // Set the prayer name and Adhan time in the TextViews
        viewHolder.textViewPrayerName.setText(pray.getNamePray());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if((pray.getTimeAdhan().isAfter(LocalTime.now())&&!nextPray)) {
                nextPray=true;
                Duration duration = Duration.between(LocalTime.now(),pray.getTimeAdhan());
                int hours = (int) duration.toHours();
                int minutes = (int) (duration.toMinutes()%60);
                viewHolder.textViewAdhanTime.setTextColor(Color.rgb(255,0,0));
                viewHolder.textViewPrayerName.setTextColor(Color.rgb(255,0,0));
                if(hours==0)
                    ((AdhanActivity)context).setReminderText("باقي " +minutes + " دقيقة");
                else if(hours==1)
                    ((AdhanActivity)context).setReminderText("باقي " + " ساعة و" + minutes + " دقيقة");
                else if(hours==2)
                    ((AdhanActivity)context).setReminderText("باقي " + " ساعتين و" + minutes + " دقيقة");
                else if (hours<=10)               ((AdhanActivity)context).setReminderText("باقي " + (hours) + " ساعات و" + minutes + " دقيقة");
                else                ((AdhanActivity)context).setReminderText("باقي " + (hours) + " ساعة و" + minutes + " دقيقة");





            }
//            else if ((pray.getPrayer().equals(Prayer.FAJR)&&!nextPray&&dataList.get(4).getTimeAdhan().isBefore(LocalTime.now()))) {
//                nextPray=true;
//                Duration duration = Duration.between(LocalTime.now(),pray.getTimeAdhan());
//                long hours = duration.toHours();
//                long minutes = duration.toMinutes()%60 ;
//                viewHolder.textViewAdhanTime.setTextColor(Color.rgb(255,0,0));
//                viewHolder.textViewPrayerName.setTextColor(Color.rgb(255,0,0));
//                if(hours==0)
//                    ((AdhanActivity)context).setReminderText("باقي " +minutes + " دقيقة");
//                else if((hours)==1)
//                    ((AdhanActivity)context).setReminderText("باقي " + " ساعة و" + minutes + " دقيقة");
//                else if((hours)==2)
//                    ((AdhanActivity)context).setReminderText("باقي " + " ساعتين و" + minutes + " دقيقة");
//                else if(hours<=10)               ((AdhanActivity)context).setReminderText("باقي " + (hours) + " ساعات و" + minutes + " دقيقة");
//                else                ((AdhanActivity)context).setReminderText("باقي " + (hours) + " ساعة و" + minutes + " دقيقة");
//
//
//            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            viewHolder.textViewAdhanTime.setText(pray.getTimeAdhan().format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        return convertView;
    }

    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView textViewPrayerName;
        TextView textViewAdhanTime;
        TextView textViewReminder;
    }


}
