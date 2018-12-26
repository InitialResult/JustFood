package com.justfood.justfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText country_code;
    private EditText phone_number;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        if (getIntent().hasExtra("phone number")) {
            String getUserPhoneNumber = getIntent().getStringExtra("phone number").substring(4);
            phone_number.setText(getUserPhoneNumber);
        }
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
    }

    public void onClick(View view) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                country_code.getText().toString()+phone_number.getText().toString(),        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks


        String code_phone_number = country_code.getText().toString() + " " + phone_number.getText().toString();
        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra("phone number", code_phone_number);
        startActivity(intent);
    }
}
