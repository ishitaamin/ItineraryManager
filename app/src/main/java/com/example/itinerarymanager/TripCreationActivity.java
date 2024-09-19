package com.example.itinerarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripCreationActivity extends AppCompatActivity {

    private EditText tripNameInput, startDateInput, endDateInput;
    private Button createTripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_creation_activity);

        // Initialize UI elements
        tripNameInput = findViewById(R.id.tripNameInput);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        createTripButton = findViewById(R.id.createTripButton);

        // Handle trip creation
        createTripButton.setOnClickListener(v -> createTrip());
    }

    private void createTrip() {
        String tripName = tripNameInput.getText().toString().trim();
        String startDateString = startDateInput.getText().toString().trim();
        String endDateString = endDateInput.getText().toString().trim();
        String userEmail = getIntent().getStringExtra("email");

        // Input validation
        if (tripName.isEmpty() || startDateString.isEmpty() || endDateString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Disable leniency to strictly check date format

        try {
            // Parse start and end dates from string to Date
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            if (endDate.before(startDate)) {
                Toast.makeText(this, "End date cannot be before start date", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert Date to Firestore Timestamp
            Timestamp startTimestamp = new Timestamp(startDate);
            Timestamp endTimestamp = new Timestamp(endDate);

            // Firestore reference
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Create a new trip document
            Map<String, Object> tripData = new HashMap<>();
            tripData.put("tripName", tripName);
            tripData.put("startDate", startTimestamp);
            tripData.put("endDate", endTimestamp);
            // Convert the String array to a List
            List<String> membersList = Arrays.asList(userEmail);
            tripData.put("members", membersList);
            tripData.put("status", "planned");

            // Show a success message and finish the activity
// In TripCreationActivity.java
            db.collection("trips")
                    .add(tripData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Trip created successfully!", Toast.LENGTH_SHORT).show();

                        // Indicate that the trip was created successfully
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);

                        // Return to the previous activity (TripListActivity)
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error creating trip: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
        }
    }

    // Optional: Reset the fields after the trip is created
    private void resetFields() {
        tripNameInput.setText("");
        startDateInput.setText("");
        endDateInput.setText("");
    }
}
