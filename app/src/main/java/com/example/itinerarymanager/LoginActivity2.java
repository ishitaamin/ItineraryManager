package com.example.itinerarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity2 extends AppCompatActivity {
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        EditText emailField = (findViewById(R.id.editTextTextEmailAddress));
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    // Get the email from the input field
                    String email = String.valueOf(emailField.getText());

                    // Create an intent to start TripListActivity
                Intent intent = new Intent(LoginActivity2.this, TripListActivity.class); // Use correct package

                // Pass the email to the TripListActivity
                    intent.putExtra("email", email);
                    startActivity(intent);
            }
        });
    }
}