package com.justfood.justfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {
    private TextView phone_number;
    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        String user_phone_number = getIntent().getStringExtra("phone number");
        phone_number = findViewById(R.id.phone_number);
        phone_number.setText("+"+user_phone_number);
        code = findViewById(R.id.code);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_phone_number:
                Intent intentLoginActivity = new Intent(this, LoginActivity.class);
                intentLoginActivity.putExtra("phone number", phone_number.getText().toString());
                startActivity(intentLoginActivity);
                break;
            case R.id.nextBtn:
                Intent intentMainActivity = new Intent(this, MainActivity.class);
                intentMainActivity.setFlags(intentMainActivity.FLAG_ACTIVITY_NEW_TASK | intentMainActivity.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentMainActivity);
        }

    }
}
