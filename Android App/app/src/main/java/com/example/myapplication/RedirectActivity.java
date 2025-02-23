package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RedirectActivity extends AppCompatActivity {

    private Button btnViewRecords, btnAddRecords, btnCheckSafety;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        btnViewRecords = findViewById(R.id.btnViewRecords);
        btnAddRecords = findViewById(R.id.btnAddRecords);
        btnCheckSafety = findViewById(R.id.btnCheckSafety);

        btnViewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement View Records functionality
                startActivity(new Intent(RedirectActivity.this, PersonGridActivity.class));
                finish();
            }
        });

        btnAddRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement Add Records functionality
                startActivity(new Intent(RedirectActivity.this, RiskCalculatorActivity.class));
                finish();
            }
        });

        btnCheckSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement Check Safety functionality
                startActivity(new Intent(RedirectActivity.this, SafetyCheckActivity.class));
                finish();
            }
        });
    }
}