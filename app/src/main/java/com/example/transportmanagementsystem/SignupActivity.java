package com.example.transportmanagementsystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText signupEmailAddress, signupPassword, signupName;
    private TextView signupTextView;
    private Button signupButton;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        signupEmailAddress = findViewById(R.id.signupEmailAddress);
        signupPassword = findViewById(R.id.signupPassword);
        signupName = findViewById(R.id.signupName);
        signupTextView = findViewById(R.id.signupTextView);
        signupButton = findViewById(R.id.signupButton);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomainActivity();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void gotomainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void createUser() {
        String name,email, password;
        name = signupName.getText().toString();
        email = signupEmailAddress.getText().toString();
        password = signupPassword.getText().toString();

        if (name.isEmpty()){
            signupEmailAddress.setError("Enter your Name");
            signupEmailAddress.requestFocus();
            return;
        }
        if (email.isEmpty()){
            signupEmailAddress.setError("Enter an email Address");
            signupEmailAddress.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signupEmailAddress.setError("Enter a valid email Address");
            signupEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()){
            signupEmailAddress.setError("Enter Password");
            signupEmailAddress.requestFocus();
            return;
        }
        if (password.length()<6){
            signupEmailAddress.setError("Password Should be at list 6 Character");
            signupEmailAddress.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserModel userModel = new UserModel(name, email, password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}