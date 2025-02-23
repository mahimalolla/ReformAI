package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PersonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        ImageView personImage = findViewById(R.id.personImage);
        TextView personName = findViewById(R.id.personName);
        TextView personRiskFactor = findViewById(R.id.personRiskFactor);
        TextView personAge = findViewById(R.id.personAge);
        TextView personLocation = findViewById(R.id.personLocation);
        TextView personCrimes = findViewById(R.id.personCrimes);

        Person person = (Person) getIntent().getSerializableExtra("person");

        if (person != null) {
            personImage.setImageResource(person.getImageResource());
            personName.setText(person.getName());
            personRiskFactor.setText("Risk Factor: " + person.getRiskFactor());
            personAge.setText("Age: " + person.getAge());
            personLocation.setText("Location: " + person.getLocation());
            personCrimes.setText("Past Crimes: " + person.getPastCrimes());

            // Change the background color of the Risk Factor based on severity
            int riskFactor = person.getRiskFactor();
            if (riskFactor >= 8) {
                personRiskFactor.setBackgroundResource(R.drawable.rounded_bg_red);
            } else {
                personRiskFactor.setBackgroundResource(R.drawable.rounded_bg_dark);
            }
        }
    }
}
