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


        tripNameInput = findViewById(R.id.tripNameInput);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        createTripButton = findViewById(R.id.createTripButton);


        createTripButton.setOnClickListener(v -> createTrip());
    }

    private void createTrip() {
        String tripName = tripNameInput.getText().toString().trim();
        String startDateString = startDateInput.getText().toString().trim();
        String endDateString = endDateInput.getText().toString().trim();
        String userEmail = getIntent().getStringExtra("email");


        if (tripName.isEmpty() || startDateString.isEmpty() || endDateString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {

            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);

            assert endDate != null;
            if (endDate.before(startDate)) {
                Toast.makeText(this, "End date cannot be before start date", Toast.LENGTH_SHORT).show();
                return;
            }


            Timestamp startTimestamp = new Timestamp(startDate);
            Timestamp endTimestamp = new Timestamp(endDate);


            FirebaseFirestore db = FirebaseFirestore.getInstance();


            Map<String, Object> tripData = new HashMap<>();
            tripData.put("tripName", tripName);
            tripData.put("startDate", startTimestamp);
            tripData.put("endDate", endTimestamp);

            List<String> membersList = Arrays.asList(userEmail);
            tripData.put("members", membersList);
            tripData.put("status", "planned");



            db.collection("trips")
                    .add(tripData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Trip created successfully!", Toast.LENGTH_SHORT).show();


                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);


                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error creating trip: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format. Please use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
        }

    }


    private void resetFields() {
        tripNameInput.setText("");
        startDateInput.setText("");
        endDateInput.setText("");
    }
}
