package com.farms.adhanapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farms.adhanapp.R;
import com.farms.adhanapp.controller.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    public TextView textView;
    public Button buttonAuto;
    public Button buttonOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textView=findViewById(R.id.city_text_view);
        buttonAuto=findViewById(R.id.auto_button);
        buttonOk=findViewById(R.id.button_ok);

//        buttonAuto.setOnClickListener(v -> {
//            if(Controller.checkPermission(this)){
//                Controller.fetchLocation(this);
//            }
//        });
//        buttonOk.setOnClickListener(v -> {
//            this.finish();
//        });

    }
}