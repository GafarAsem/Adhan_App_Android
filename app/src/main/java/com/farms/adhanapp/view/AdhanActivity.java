package com.farms.adhanapp.view;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.farms.adhanapp.R;
import com.farms.adhanapp.controller.DataController;
import com.farms.adhanapp.controller.LocationController;
import com.farms.adhanapp.controller.PraysController;
import com.farms.adhanapp.services.GridAdapter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;


public class AdhanActivity extends AppCompatActivity {

    GridView gridView;
    ListView listView;
    TextView textReminder;
    TextView textTitle;
    TextView textCity;
    ImageButton editCityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhan);

        listView=findViewById(R.id.activity_adhan_list_view_7days_pray);
        gridView=findViewById(R.id.activity_adhan_grid_view_prays_day);
        textReminder=findViewById(R.id.activity_adhan_text_view_reminder);
        textTitle=findViewById(R.id.activity_adhan_text_view_date_hijri);
        textCity=findViewById(R.id.activity_adhan_text_view_city_name);
        editCityButton =findViewById(R.id.activity_adhan_button_city_edit);

        if (DataController.isDataExist(this)) {
            DataController.fetchData(this);
            PraysController.fetchPrays(this);
            setViews();
        }else startActivityForResult(new Intent(this,SettingActivity.class),1);

        editCityButton.setOnClickListener(v -> {

            startActivityForResult(new Intent(this,SettingActivity.class),1);

        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DataController.isDataExist(this)) {
            DataController.fetchData(this);
            PraysController.fetchPrays(this);
            setViews();
        }else finish();
    }

    private void setViews() {
        textTitle.setText(HijrahDate.now().format(DateTimeFormatter.ofPattern("EEEE dd MMMM",new Locale("ar"))));
        textCity.setText(LocationController.searchCityName(this));
        gridView.setAdapter(new GridAdapter(this,Arrays.asList(PraysController.fetchPrays(this))));
    }


    public void setReminderText() {

        try {
            Duration duration = Duration.between(LocalDateTime.now(), PraysController.getNextPray(LocalDateTime.now()).getDateTimePray());
            int hours = (int) duration.toHours();
            int minutes = ((int) duration.toMinutes() % 60) + 1;
            textReminder.setText("باقي " + getHourStr(hours) + (((hours != 0) && (minutes != 0)) ? "و" : "") + getMinStr(minutes) + " للصلاة القادمة");
        }catch (Exception ignored){
        }
    }
    private String getMinStr(int minutes) {
        if(minutes==0)
            return " ";
        else if(minutes==1)
            return "دقيقة";
        else if (minutes==2)
            return "دقيقتين";
        else if (minutes<=10)
            return minutes+" دقائق";
        else
            return minutes+" دقيقة";
    }

    private String getHourStr(int hours) {
        if(hours==0)
            return " ";
        else if(hours==1)
            return "ساعة ";
        else if (hours==2)
            return "ساعتين ";
        else if (hours<=10)
            return hours+" ساعات ";
        else
            return hours+" ساعة ";
    }

}