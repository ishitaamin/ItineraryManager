package com.example.itinerarymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarymanager.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;

public class TripListActivity extends AppCompatActivity {

    private static final int TRIP_CREATION_REQUEST_CODE = 1; // Request code for trip creation activity
    private LinearLayout tripsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_list_activity);

        // Get the email passed from FirstActivity
        String userEmail = getIntent().getStringExtra("email");

        tripsContainer = findViewById(R.id.tripsContainer);
        Button addTripButton = findViewById(R.id.addTripButton);

        // Load trips from Firestore based on the user's email when activity starts
        loadTripsFromFirestore(userEmail);

        addTripButton.setOnClickListener(v -> {
            // Start TripCreationActivity using startActivityForResult to pause the current activity
            Intent intent = new Intent(TripListActivity.this, TripCreationActivity.class);
            intent.putExtra("email", userEmail); // Pass the user's email
            startActivityForResult(intent, TRIP_CREATION_REQUEST_CODE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Nothing to do here since trips are reloaded only when coming back from TripCreationActivity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TRIP_CREATION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Reload trips only when a new trip is created
            String userEmail = getIntent().getStringExtra("email");
            loadTripsFromFirestore(userEmail);
        }
    }


    @SuppressLint("SetTextI18n")
    private void loadTripsFromFirestore(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Clear the container first to avoid duplicates
        tripsContainer.removeAllViews();

        db.collection("trips")
                .whereArrayContains("members", email)  // Query trips where the user is a member
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> trip = document.getData();
                            String tripName = Objects.requireNonNull(trip.get("tripName")).toString();  // Updated from "name" to "tripName"

                            // Convert Firestore Timestamp to Java Date and format it
                            Timestamp startTimestamp = (Timestamp) trip.get("startDate");
                            Timestamp endTimestamp = (Timestamp) trip.get("endDate");

                            // Define the date format you want
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

                            // Convert Timestamp to Date and then format it
                            String startDate = sdf.format(startTimestamp.toDate());
                            String endDate = sdf.format(endTimestamp.toDate());

                            // Create a card to display trip details
                            addTripCard(tripName, startDate, endDate);
                        }
                    } else {
                        // Show "Nothing to show here" if no trips
                        TextView emptyTextView = new TextView(this);
                        emptyTextView.setText("Nothing to show here");
                        tripsContainer.addView(emptyTextView);
                    }
                });
    }

    // Method to create a card for each trip
    @SuppressLint("SetTextI18n")
    private void addTripCard(String tripName, String startDate, String endDate) {
        TextView tripCard = new TextView(this);
        tripCard.setText(tripName + "\n" + "From: " + startDate + " To: " + endDate);
        tripCard.setPadding(16, 16, 16, 16);
        tripCard.setBackgroundResource(R.drawable.trip_card_background);  // Add a custom background to make it look like a card
        tripsContainer.addView(tripCard);
    }
}
