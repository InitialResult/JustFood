package com.justfood.justfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {
    private EditText country_code;
    private EditText phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_no, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        phone_number = findViewById(R.id.phone_number);
        phone_number.requestFocus();
        if (spinner.getSelectedItem().toString().equals("United Kingdom")) {
            country_code = findViewById(R.id.country_code);
            country_code.setText("44");
        }
    }
}
