package com.example.itinerarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TripDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_details);

        // Find the TextViews for each day
        TextView day1TextView = findViewById(R.id.day_1);
        TextView day2TextView = findViewById(R.id.day_2);
        TextView day3TextView = findViewById(R.id.day_3);
        TextView tripMembers = findViewById(R.id.trip_members);
        TextView tripDocuments = findViewById(R.id.trip_documents);


        // Set an onClickListener for "Day 1"
        day1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to move to ItineraryActivity
                Intent intent = new Intent(TripDetailsActivity.this, ItineraryActivity.class);
                intent.putExtra("day", "Day 1"); // Pass "Day 1" information
                startActivity(intent); // Start the ItineraryActivity
            }
        });

        // Set an onClickListener for "Day 2"
        day2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripDetailsActivity.this, ItineraryActivity.class);
                intent.putExtra("day", "Day 2"); // Pass "Day 2" information
                startActivity(intent);
            }
        });

        // Set an onClickListener for "Day 3"
        day3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripDetailsActivity.this, ItineraryActivity.class);
                intent.putExtra("day", "Day 3"); // Pass "Day 3" information
                startActivity(intent);
            }
        });

        tripMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripDetailsActivity.this, members.class);
                intent.putExtra("day", "Day 3"); // Pass "Day 3" information
                startActivity(intent);
            }
        });

        tripDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripDetailsActivity.this, documents.class);
                intent.putExtra("day", "Day 3"); // Pass "Day 3" information
                startActivity(intent);
            }
        });

    }
}
