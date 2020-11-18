package com.example.hariif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    TextInputLayout regName, regUsername,
            regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    FirebaseAuth auth;
    DatabaseReference reference;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
//Save data in FireBase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("User").child("Client");
//Get all the values

                final String name = regName.getEditText().getText().toString();
                final String username = regUsername.getEditText().getText().toString();
                final String email = regEmail.getEditText().getText().toString();
                final String phoneNo = regPhoneNo.getEditText().getText().toString();
                final String password = regPassword.getEditText().getText().toString();
                if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Fill up empty case", Toast.LENGTH_SHORT).show();


                } else {
               auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                   UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password,key);
                                reference.push().setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "register Succefully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(RegisterActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        }//Register Button method end
    });//onCreate Meth

    }}

