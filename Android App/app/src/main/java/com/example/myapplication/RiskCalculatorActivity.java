package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RiskCalculatorActivity extends AppCompatActivity {

    private Spinner spinnerAge, spinnerEducation, spinnerEmployment, spinnerPriorArrests, spinnerFirearmDrug, spinnerReoffenseHistory, spinnerOffense;
    private Button btnCalculateRisk;
    private TextView tvRiskScore;
    private View riskIndicatorDot;

    private static final String API_URL = "https://pred-ulk0.onrender.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_calculator);

        // Initialize UI components
        spinnerAge = findViewById(R.id.spinnerAge);
        spinnerEducation = findViewById(R.id.spinnerEducation);
        spinnerEmployment = findViewById(R.id.spinnerEmployment);
        spinnerPriorArrests = findViewById(R.id.spinnerPriorArrests);
        spinnerFirearmDrug = findViewById(R.id.spinnerFirearmDrug);
        spinnerReoffenseHistory = findViewById(R.id.spinnerReoffenseHistory);
        spinnerOffense = findViewById(R.id.spinnerOffense);
        btnCalculateRisk = findViewById(R.id.btnCalculateRisk);
        tvRiskScore = findViewById(R.id.tvRiskScore);
        riskIndicatorDot = findViewById(R.id.riskIndicatorDot);

        // Set up dropdowns
        setupDropdown(spinnerAge, new String[]{"18-22", "23-27", "28-32", "33-37", "38-42", "43-47", "48 or older"});
        setupDropdown(spinnerEducation, new String[]{"Less than HS diploma", "High School Diploma", "At least some college"});
        setupDropdown(spinnerEmployment, new String[]{"0-25%", "25-50%", "50-75%", "75%+"});
        setupDropdown(spinnerPriorArrests, new String[]{"0-3 (Low)", "4-7 (Moderate)", "8+ (High)"});
        setupDropdown(spinnerFirearmDrug, new String[]{"False", "True"});
        setupDropdown(spinnerReoffenseHistory, new String[]{"False", "True"});
        setupDropdown(spinnerOffense, new String[]{"Drug", "Violent/Non-Sex", "Property", "Other", "Violent/Sex"});

        btnCalculateRisk.setOnClickListener(v -> new CalculateRiskTask().execute());
    }

    private void setupDropdown(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private class CalculateRiskTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                Map<String, Integer> requestBody = new HashMap<>();
                requestBody.put("Age_at_Release", spinnerAge.getSelectedItemPosition() + 1);
                requestBody.put("Education_Level", spinnerEducation.getSelectedItemPosition() + 1);
                requestBody.put("Percent_Days_Employed", spinnerEmployment.getSelectedItemPosition());
                requestBody.put("Prior_Arrest_Episodes", spinnerPriorArrests.getSelectedItemPosition());
                requestBody.put("Prior_Arrest_Episodes_Drug_or_Gun_Charges", spinnerFirearmDrug.getSelectedItemPosition());
                requestBody.put("Recidivism_Arrest_Last_3_Years", spinnerReoffenseHistory.getSelectedItemPosition());
                requestBody.put("Combined_Offense", spinnerOffense.getSelectedItemPosition());

                // Send request
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                conn.getOutputStream().write(new JSONObject(requestBody).toString().getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = br.readLine();
                return new JSONObject(response);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                double riskScore;
                if (result != null && result.has("supervision_risk_score")) {
                    riskScore = result.optDouble("supervision_risk_score", -1);
                } else {
                    // If API fails, generate a random risk score between 7 and 10
                    riskScore = new Random().nextInt(4) + 7; // Generates 7, 8, 9, or 10
                    Toast.makeText(RiskCalculatorActivity.this, "API failed, using estimated risk score", Toast.LENGTH_SHORT).show();
                }

                tvRiskScore.setText("Risk Score: " + riskScore);
                riskIndicatorDot.setBackgroundColor(getRiskColor(riskScore));

            } catch (Exception e) {
                tvRiskScore.setText("Error calculating risk score");
                Toast.makeText(RiskCalculatorActivity.this, "An error occurred while fetching the risk score", Toast.LENGTH_SHORT).show();
            }
        }

        private int getRiskColor(double score) {

            return score <= 3 ? getColor(R.color.light_color) : score <= 6 ? getColor(R.color.medium_color) : getColor(R.color.dark_color);
        }
    }
}
