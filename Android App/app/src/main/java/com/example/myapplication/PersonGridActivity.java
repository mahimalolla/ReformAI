package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PersonGridActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private List<Person> allPersons;
    private List<Person> selectedPersons;

    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_grid);

        gridView = findViewById(R.id.gridView);

        // Initialize list with 15 persons
        allPersons = new ArrayList<>();
        populatePersons();

        // Randomly select 9 persons
        selectedPersons = new ArrayList<>(allPersons);
        Collections.shuffle(selectedPersons);
        selectedPersons = selectedPersons.subList(0, 9);

        // Set up the adapter
        PersonGridAdapter adapter = new PersonGridAdapter(this, selectedPersons);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = selectedPersons.get(position);
            Intent intent = new Intent(PersonGridActivity.this, PersonDetailActivity.class);
            intent.putExtra("person", selectedPerson);
            startActivity(intent);
        });
    }


    private void populatePersons() {
        allPersons.add(new Person("John Doe", R.drawable.img_1, 8, 35, "New York, NY", "Fraud, Theft"));
        allPersons.add(new Person("Jane Smith", R.drawable.img_2, 6, 29, "Los Angeles, CA", "Burglary"));
        allPersons.add(new Person("Robert Brown", R.drawable.img_3, 9, 42, "Chicago, IL", "Murder, Assault"));
        allPersons.add(new Person("Emily Davis", R.drawable.img_4, 5, 31, "Houston, TX", "Drug Possession"));
        allPersons.add(new Person("Michael Wilson", R.drawable.img_5, 7, 38, "Miami, FL", "Arson"));
        allPersons.add(new Person("Sophia Miller", R.drawable.img_6, 4, 27, "Seattle, WA", "Vandalism"));
        allPersons.add(new Person("Daniel Martinez", R.drawable.img_7, 6, 34, "Boston, MA", "Forgery"));
        allPersons.add(new Person("Olivia Taylor", R.drawable.img_8, 9, 45, "Denver, CO", "Kidnapping"));
        allPersons.add(new Person("David Anderson", R.drawable.img_9, 3, 22, "San Diego, CA", "Petty Theft"));
        allPersons.add(new Person("Liam Thomas", R.drawable.img_10, 5, 33, "Dallas, TX", "Embezzlement"));
        allPersons.add(new Person("Ava Harris", R.drawable.img_11, 7, 40, "Philadelphia, PA", "Robbery"));
        allPersons.add(new Person("Noah White", R.drawable.img_12, 8, 36, "Phoenix, AZ", "Assault"));
        allPersons.add(new Person("Isabella Lewis", R.drawable.img_13, 6, 28, "San Antonio, TX", "Extortion"));
        allPersons.add(new Person("James Scott", R.drawable.img_14, 7, 30, "San Jose, CA", "Bribery"));
        allPersons.add(new Person("Charlotte Young", R.drawable.img_15, 4, 26, "Indianapolis, IN", "Cybercrime"));
    }
}
