package com.farms.adhanapp.setting.view;

import android.app.ProgressDialog;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.batoulapps.adhan2.CalculationMethod;
import com.farms.adhanapp.R;
import com.farms.adhanapp.setting.helper.DataHelper;
import com.farms.adhanapp.location.helper.LocationHelper;
import com.farms.adhanapp.permission.helper.PermissionHelper;

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
        if(DataHelper.isDataExist(this))
            setData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Set to spinner style
        progressDialog.setIndeterminate(true); // Set indeterminate progress
        progressDialog.setCancelable(false); // Optional: prevent user from dismissing

        buttonSearch.setOnClickListener(v->{
            if(PermissionHelper.requestPermission(this,"android.permission.ACCESS_FINE_LOCATION")){
                progressDialog.show();
                LocationHelper.fetchLocationUpdates(this, () -> {
                    progressDialog.hide();
                    textViewCityName.setText(LocationHelper.cityName);
                });
            }
        });
        buttonSave.setOnClickListener(v -> {

            DataHelper.saveData(this, (float) LocationHelper.getLocation().getLatitude(), (float) LocationHelper.getLocation().getLongitude(),textViewCityName.getText().toString());
            this.finish();
        });

    }




    private void setData() {
        //switchAutoLocation.setChecked();
//        spinnerCalculationMethod
        editTextIshaAngle.setText(DataHelper.getFloat(this,"fajr angle").toString());
        editTextIshaAngle.setText(DataHelper.getFloat(this,"isha angle").toString());
        textViewCityName.setText(DataHelper.getString(this,"city"));
    }
}