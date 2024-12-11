package com.finalproj.kleplacementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class CompanyCommonActivity extends AppCompatActivity {

    TextView titletext,descriptiontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_common);
        titletext=findViewById(R.id.titletext);
        descriptiontext=findViewById(R.id.descriptiontext);

        Intent intent = getIntent();
        String t=intent.getStringExtra("titletext1");
        String d=intent.getStringExtra("descriptiontext1");

        titletext.setText(t.toString());
        descriptiontext.setText(d.toString());
        Linkify.addLinks(descriptiontext, Linkify.WEB_URLS);
    }
}