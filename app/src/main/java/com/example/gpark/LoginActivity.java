package com.example.gpark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText emmail, pass;
    private FirebaseAuth mAuth;
//    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog( this );
        emmail =  findViewById(R.id.editText_email);
        pass =  findViewById(R.id.editText_passwordLogIn);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_forgetPasswortLogIn).setOnClickListener(this);
        findViewById(R.id.button_registerLogIn).setOnClickListener(this);
        findViewById(R.id.button_logInLogIn).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_forgetPasswortLogIn:
                //progressDialog.setMessage( "Loading..." );
               // progressDialog.show();
                startActivity(new Intent(LoginActivity.this, ForgetpassActivity.class));
                Toast.makeText(getApplicationContext(), "Clicked on Forget Password",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_registerLogIn:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                //Toast.makeText(getApplicationContext(), "Clicked on Registration",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_logInLogIn:
                check_login();
        }
    }

    private void check_login() {
        String email = emmail.getText().toString().trim();
        String passw = pass.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()){
            emmail.setError("Email should not be empty!");
            emmail.requestFocus();
            return;
        }else if (passw.isEmpty()){
            pass.setError("Password!");
            pass.requestFocus();
            return;
        }else if (passw.length()<5){
            pass.setError("Password length should be 5 or more!");
            pass.requestFocus();
            return;
        }else if (!email.matches(emailPattern)){
            emmail.setError("Incorrect email address!");
            emmail.requestFocus();
            return;
        }else {
            mAuth.signInWithEmailAndPassword(email, passw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,HomepageActivity.class);
                                startActivity(intent);
                                finish();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }
}
