package com.example.itinerarymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class documents extends AppCompatActivity {



        private LinearLayout linearLayoutDocuments;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_documents); // replace with your layout file name

            linearLayoutDocuments = findViewById(R.id.linearLayoutDocuments);
            Button btnAddDocument = findViewById(R.id.addDocumentButton);

            btnAddDocument.setOnClickListener(v -> addDocumentItem());
        }

        // Method to dynamically add document item views
        private void addDocumentItem() {
            View documentView = getLayoutInflater().inflate(R.layout.document_item, null); // Inflate the layout for each document

            // Handle the remove button in each document view
            Button btnRemove = documentView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(v -> linearLayoutDocuments.removeView(documentView));

            // Add the newly created document view to the LinearLayout
            linearLayoutDocuments.addView(documentView);
        }
    }

