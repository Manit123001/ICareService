package com.example.mcnewz.icareservice.jamelogin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.jamelogin.manager.config;

public class ShowCodeActivity extends AppCompatActivity {
    TextView txtCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);
        txtCode = (TextView)findViewById(R.id.txtCode);
        txtCode.setText(config.idcode);
    }
}
