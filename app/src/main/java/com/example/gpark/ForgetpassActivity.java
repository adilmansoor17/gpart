package com.example.gpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetpassActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        findViewById(R.id.button_need_to_reg).setOnClickListener(this);
        findViewById(R.id.button_back_to_LogIn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_need_to_reg:
                startActivity(new Intent(ForgetpassActivity.this, RegistrationActivity.class));
                Toast.makeText(getApplicationContext(), "Clicked on Registratin",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_back_to_LogIn:
                startActivity(new Intent(ForgetpassActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(), "Back to LogIn",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
