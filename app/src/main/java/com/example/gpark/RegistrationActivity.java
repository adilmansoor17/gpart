package com.example.gpark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText _fullName, _phnNum, _address, _vehicleRegNo, _passwprd;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView vhl_type;
    private String vehicleType = "Student";
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _fullName = findViewById(R.id.editText_nmfull);
        _phnNum = findViewById(R.id.editText_phnnumReg);
        _address = findViewById(R.id.editText_add);
        _vehicleRegNo = findViewById(R.id.editText_VehicleRegNum);
        _passwprd = findViewById(R.id.editText_passwasdordsReg);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_regdffdisterLogIn).setOnClickListener(this);
        findViewById(R.id.button_regSave).setOnClickListener(this);
        findViewById(R.id.radioOne).setOnClickListener(this);
        findViewById(R.id.radioTwo).setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_regdffdisterLogIn:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(), "Clicked on Need to LogIn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioOne:
                vehicleType="Faculty";
                Toast.makeText(getApplicationContext(), vehicleType,Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioTwo:
                vehicleType="Student";
                Toast.makeText(getApplicationContext(), vehicleType,Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_regSave:
                info_save();
                Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void info_save() {
        String fullName = _fullName.getText().toString().trim();
        String phnNum = _phnNum.getText().toString().trim();
        String address = _address.getText().toString().trim();
        String vehicleRegNo = _vehicleRegNo.getText().toString().trim();
        String password = _passwprd.getText().toString().trim();

        if (fullName.isEmpty()){
            _fullName.setError("Name is required");
            _fullName.requestFocus();
            return;
        }else if (phnNum.isEmpty()){
            _phnNum.setError("Phone number is required");
            _phnNum.requestFocus();
            return;
        }else if (address.isEmpty()){
            _address.setError("Address is required");
            _address.requestFocus();
            return;
        }else if (vehicleRegNo.isEmpty()){
            _vehicleRegNo.setError("Registration number is required");
            _vehicleRegNo.requestFocus();
            return;
        }else if (password.isEmpty()){
            _passwprd.setError("Password is required");
            _passwprd.requestFocus();
            return;
        }else if (password.length()<5){
            _passwprd.setError("Password length should be 5 or more!");
            _passwprd.requestFocus();
            return;
        }else {
            mAuth.createUserWithEmailAndPassword(address, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);

                                Map<String, Object> fuser = new HashMap<>();
                                fuser.put("fullname", fullName);
                                fuser.put("phonenumber", phnNum);
                                fuser.put("email", address);
                                fuser.put("user_type", vehicleType);
                                fuser.put("registrationnumber",vehicleRegNo);

                                db.collection("users")
                                        .add(fuser)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                Toast.makeText(RegistrationActivity.this, "Created user ID: " + documentReference.getId(),
                                                        Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error adding to cloud firestore", e);
                                                Toast.makeText(RegistrationActivity.this, "Error adding to cloud firestore",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed: cannot create user",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
//            });

        }





    }

}
