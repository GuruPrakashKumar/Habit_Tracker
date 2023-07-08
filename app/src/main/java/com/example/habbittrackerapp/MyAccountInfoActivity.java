package com.example.habbittrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyAccountInfoActivity extends AppCompatActivity {

    private EditText profileNameEditText;
    private EditText birthdateEditText;
    private EditText heightEditText;
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_info);

        profileNameEditText = findViewById(R.id.profileNameEditText);
        birthdateEditText = findViewById(R.id.birthdateEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedProfileName = sharedPreferences.getString("profileName", "");
        profileNameEditText.setText(storedProfileName);
        birthdateEditText.setText(sharedPreferences.getString("birthDate", ""));
        heightEditText.setText(sharedPreferences.getString("height", ""));
        weightEditText.setText(sharedPreferences.getString("weight", ""));

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccountInfo();
                onBackPressed();
            }
        });
    }

    private void saveAccountInfo() {
        String profileName = profileNameEditText.getText().toString();
        String birthdate = birthdateEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();



        // Storing the profile name using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", profileName);
        editor.putString("birthDate",birthdate);
        editor.putString("height",height);
        editor.putString("weight",weight);
        editor.apply();
    }
}
