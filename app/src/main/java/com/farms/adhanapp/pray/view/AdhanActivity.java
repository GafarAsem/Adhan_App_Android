package com.farms.adhanapp.pray.view;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.farms.adhanapp.R;
import com.farms.adhanapp.permission.helper.PermissionHelper;
import com.farms.adhanapp.setting.helper.DataHelper;
import com.farms.adhanapp.setting.view.SettingActivity;
import com.farms.adhanapp.location.helper.LocationHelper;
import com.farms.adhanapp.pray.helper.PraysHelper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AdhanActivity extends AppCompatActivity {

    GridView gridView;
    ListView listView;
    TextView textReminder;
    TextView textTitle;
    TextView textCity;
    ImageButton editCityButton;

    private void showLocationDialog() {
        // Location dialog implementation
        if(PermissionHelper.requestPermission(this,"android.permission.ACCESS_FINE_LOCATION")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("الموقع")
                    .setMessage("هل تريد تحديد موقعك")
                    .setPositiveButton("نعم", (dialog, which) -> {
                        // Open location picker or any other action
                        // For simplicity, let's just show a toast
                        ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("جار العثور على الموقع...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Set to spinner style
                        progressDialog.setIndeterminate(true); // Set indeterminate progress
                        progressDialog.setCancelable(false); // Optional: prevent user from dismissing

                        progressDialog.show();
                        LocationHelper.fetchLocationUpdates(this, () -> {
                            progressDialog.hide();
                            textCity.setText(LocationHelper.cityName);
//                                DataHelper.saveData();
                            PraysHelper.setPrays();
                            setViews();
                            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                            exec.scheduleAtFixedRate(this::setReminderText, 0, 1, TimeUnit.SECONDS);
                        });


                    })
                    .setNegativeButton("لا", (dialog, which) -> {
                        // User doesn't want to pick a location
                        dialog.dismiss();
                    });
            builder.create().show();
        }
    }

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

        textTitle.setText(HijrahDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM",new Locale("ar"))));

        if (DataHelper.isDataExist(this)) {
            DataHelper.fetchData(this);
            PraysHelper.setPrays();
            setViews();
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(this::setReminderText, 0, 1, TimeUnit.SECONDS);
        }
//        else startActivityForResult(new Intent(this, SettingActivity.class),1);
//
        editCityButton.setOnClickListener(v -> {
            showLocationDialog();

        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show location dialog
                showLocationDialog();
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "تم رفض الوصول للموقع", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DataHelper.isDataExist(this)) {
            DataHelper.fetchData(this);
            PraysHelper.setPrays();
            setViews();
        }else finish();
    }

    private void setViews() {

        textCity.setText(LocationHelper.cityName);
        gridView.setAdapter(new GridAdapter(this,Arrays.asList(PraysHelper.setPrays())));
    }


    @SuppressLint("SetTextI18n")
    public void setReminderText() {

        try {
            Duration duration = Duration.between(LocalDateTime.now(), PraysHelper.getNextPray().getDateTimePray());
            int hours = (int) duration.toHours();
            int minutes = ((int) duration.toMinutes() % 60) + 1;
            int seconds=  ((int) duration.getSeconds()%60) + 1;
//            textReminder.setText("باقي " + getHourStr(hours) + (((hours != 0) && (minutes != 0)) ? "و" : "") + getMinStr(minutes) + " للصلاة القادمة");
        textReminder.setText("باقي " +hours+":"+minutes+":"+seconds+ " للصلاة القادمة");
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