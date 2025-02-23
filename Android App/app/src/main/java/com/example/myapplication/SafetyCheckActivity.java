package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SafetyCheckActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Spinner spinnerStreet, spinnerDistrict;
    private Button btnCheckSafety;
    private TextView tvSafetyResult;
    private MapView mapView;
    private GoogleMap googleMap;

    private static final String BASE_URL = "https://flask-api-render-ca0i.onrender.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_check);

        spinnerStreet = findViewById(R.id.spinnerStreet);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        btnCheckSafety = findViewById(R.id.btnCheckSafety);
        tvSafetyResult = findViewById(R.id.tvSafetyResult);
        mapView = findViewById(R.id.mapView);

        // Initialize MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Populate dropdown menus
        setupSpinners();

        btnCheckSafety.setOnClickListener(v -> {
            String street = spinnerStreet.getSelectedItem().toString();
            String district = spinnerDistrict.getSelectedItem().toString();

            if (street.isEmpty() || district.isEmpty()) {
                Toast.makeText(SafetyCheckActivity.this, "Select both fields", Toast.LENGTH_SHORT).show();
            } else {
                new FetchSafetyData().execute(street, district);
            }
        });
    }

    private void setupSpinners() {
        // List of streets
        String[] streets = {"Select a street", "WASHINGTON ST", "W BROADWAY", "ALBANY ST", "DORCHESTER AVE",
                "NEW SUDBURY ST", "ALLSTATE ROAD", "DUDLEY ST", "STEDMAN ST", "LINCOLN STREET",
                "BOYLSTON ST", "W SPRINGFIELD ST", "BLUE HILL AVE", "NORFOLK AVE", "MERIDIAN ST", "CONGRESS ST"};

        // List of districts
        String[] districts = {"Select a district", "D14", "B2", "C6", "D4", "A1", "E13", "E5", "B3", "C11", "A7", "E18", "A15"};

        // Set adapters with custom layout
        ArrayAdapter<String> streetAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, streets);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStreet.setAdapter(streetAdapter);

        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }

    private class FetchSafetyData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String street = params[0];
            String district = params[1];

            try {
                String encodedStreet = URLEncoder.encode(street, "UTF-8");
                String encodedDistrict = URLEncoder.encode(district, "UTF-8");
                String apiUrl = BASE_URL + "safety-index?street=" + encodedStreet + "&district=" + encodedDistrict;

                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                return new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                try {
                    // Extracting values from API response
                    String safetyLevel = result.getString("safety_level");
                    double latitude = result.getDouble("latitude");
                    double longitude = result.getDouble("longitude");

                    // Display safety level
                    tvSafetyResult.setText("Safety Level: " + safetyLevel);

                    // Set marker color based on safety level
                    float color;
                    if (safetyLevel.contains("Red")) {
                        color = BitmapDescriptorFactory.HUE_RED;
                    } else if (safetyLevel.contains("Orange")) {
                        color = BitmapDescriptorFactory.HUE_ORANGE;
                    } else {
                        color = BitmapDescriptorFactory.HUE_GREEN;
                    }

                    // Show location on Google Maps
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title("Safety Level: " + safetyLevel)
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

                } catch (Exception e) {
                    tvSafetyResult.setText("Error parsing response");
                    e.printStackTrace();
                }
            } else {
                tvSafetyResult.setText("Error fetching data. Try again.");
            }
        }
    }
}
