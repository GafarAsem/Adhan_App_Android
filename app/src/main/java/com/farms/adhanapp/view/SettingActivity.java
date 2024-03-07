package com.farms.adhanapp.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.batoulapps.adhan2.CalculationMethod;
import com.farms.adhanapp.R;
import com.farms.adhanapp.controller.DataController;
import com.farms.adhanapp.controller.LocationController;
import com.farms.adhanapp.controller.PermissionController;
import com.farms.adhanapp.model.Location;

public class SettingActivity extends AppCompatActivity {


    private Button buttonSave;
    private Button buttonSearch;
    private Switch switchAutoLocation;
    private Spinner spinnerCalculationMethod;
    private EditText editTextFajrAngle;
    private EditText editTextIshaAngle;
    private TextView textViewCityName;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        buttonSearch=findViewById(R.id.activity_setting_button_search_city);
        buttonSave=findViewById(R.id.button_ok);
        switchAutoLocation=findViewById(R.id.activity_setting_switch_auto_location);
        spinnerCalculationMethod=findViewById(R.id.activity_setting_spinner_calculation_method);
        editTextFajrAngle=findViewById(R.id.activity_setting_edit_text_fajr_angle);
        editTextIshaAngle=findViewById(R.id.activity_setting_edit_text_isha_angle);
        textViewCityName=findViewById(R.id.activity_setting_text_view_city_name);
        if(DataController.isDataExist(this))
            setData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Set to spinner style
        progressDialog.setIndeterminate(true); // Set indeterminate progress
        progressDialog.setCancelable(false); // Optional: prevent user from dismissing

        buttonSearch.setOnClickListener(v->{
            if(PermissionController.requestPermission(this,"android.permission.ACCESS_FINE_LOCATION")){
                progressDialog.show();
                LocationController.fetchLocationUpdates(this, () -> {
                    progressDialog.hide();
                    textViewCityName.setText(LocationController.searchCityName(this));
                });
            }
        });
        buttonSave.setOnClickListener(v -> {

            DataController.saveData(this, (float) LocationController.getLocation().getLatitude(), (float) LocationController.getLocation().getLongitude(),textViewCityName.getText().toString(), CalculationMethod.MUSLIM_WORLD_LEAGUE,switchAutoLocation.isChecked(),Float.parseFloat(editTextFajrAngle.getText().toString()),Float.parseFloat(editTextIshaAngle.getText().toString()));
            this.finish();
        });

    }




    private void setData() {
        //switchAutoLocation.setChecked();
//        spinnerCalculationMethod
        editTextIshaAngle.setText(DataController.getFloat(this,"fajr angle").toString());
        editTextIshaAngle.setText(DataController.getFloat(this,"isha angle").toString());
        textViewCityName.setText(DataController.getString(this,"city"));
    }
}