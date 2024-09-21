package com.example.itinerarymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TripDetailsActivity extends AppCompatActivity {

    private LinearLayout daysContainer;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        daysContainer = findViewById(R.id.days_container);

        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        String startDateStr = intent.getStringExtra("startDate");
        String endDateStr = intent.getStringExtra("endDate");
        String location = intent.getStringExtra("location");
        String status = intent.getStringExtra("status");

        TextView tripNameTextView = findViewById(R.id.trip_name);
        TextView tripDatesTextView = findViewById(R.id.trip_dates);
        TextView tripLocationTextView = findViewById(R.id.trip_location);
        TextView tripStatusTextView = findViewById(R.id.trip_status);

        tripNameTextView.setText(tripName);
        tripDatesTextView.setText("From: " + startDateStr + " To: " + endDateStr);
        tripLocationTextView.setText(location);
        tripStatusTextView.setText(status);

        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            if (startDate != null && endDate != null) {
                addDays(startDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addDays(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int dayCount = 1;

        while (!calendar.getTime().after(endDate)) {
            View dayCardView = getLayoutInflater().inflate(R.layout.day_card, daysContainer, false);
            TextView day_name = dayCardView.findViewById(R.id.day_title);
            TextView day_date = dayCardView.findViewById(R.id.day_date);
            String dayLabel = "Day " + dayCount;
            TextView dayTextView = new TextView(this);
            day_name.setText(dayLabel);
            day_date.setText(sdf.format(calendar.getTime()));
            daysContainer.addView(dayCardView);

            dayCardView.setOnClickListener(v -> {
                Intent intent = new Intent(TripDetailsActivity.this, ItineraryActivity.class);
                intent.putExtra("day", dayLabel);
                startActivity(intent);
            });

            calendar.add(Calendar.DATE, 1);
            dayCount++;
        }
    }
}
