package com.example.itinerarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ItineraryActivity extends AppCompatActivity {
    private Button addActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        // Initialize the button
        addActivityButton = findViewById(R.id.addActivityButton);

        // Set click listener for the button
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to navigate to AddActivity
                Intent intent = new Intent(ItineraryActivity.this, add_activity.class);
                startActivity(intent);
            }
        });
    }
}
