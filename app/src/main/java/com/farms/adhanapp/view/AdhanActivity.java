package com.farms.adhanapp.view;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.farms.adhanapp.R;
import com.farms.adhanapp.controller.Controller;
import com.farms.adhanapp.controller.DataController;
import com.farms.adhanapp.controller.LocationController;
import com.farms.adhanapp.controller.PraysController;
import com.farms.adhanapp.services.ListAdapter;

import java.util.Arrays;


public class AdhanActivity extends AppCompatActivity {

    ListView listView;
    Button searchButton;
    TextView textReminder;

    public  void setList() {
        listView.setAdapter(new ListAdapter(this,Arrays.asList(PraysController.fetchPrays())));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhan);
        listView=findViewById(R.id.list_view);
        searchButton=findViewById(R.id.adhan_activity_button_search);
        textReminder=findViewById(R.id.text_reminder_time);


        if(DataController.isDataExist(this))
        {
            DataController.fetchData(this);
            setList();
            searchButton.setVisibility(View.INVISIBLE);
        }
        else {
            searchButton.setOnClickListener(v -> {
                    LocationController.fetchLocationUpdates(this, () -> {
                        DataController.saveData(this, LocationController.getLocation(), PraysController.fetchPrays());
                        setList();
                        searchButton.setVisibility(View.INVISIBLE);

                    });

            });
        }


    }


    public void setReminderText(String s) {
        textReminder.setText(s);
    }
}