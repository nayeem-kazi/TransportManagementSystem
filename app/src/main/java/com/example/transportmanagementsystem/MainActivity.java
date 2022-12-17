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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText loginEmailAddress, loginPassword;
    private TextView loginTextView;
    private Button loginButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmailAddress = findViewById(R.id.loginEmailAddress);
        loginPassword = findViewById(R.id.loginPassword);
        loginTextView = findViewById(R.id.loginTextView);
        loginButton = findViewById(R.id.loginButton);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        gotoHomePage();
    }

    private void loginUser() {
        String email, password;
        email = loginEmailAddress.getText().toString();
        password = loginPassword.getText().toString();

        
        if (email.isEmpty()){
            loginEmailAddress.setError("Enter an email Address");
            loginEmailAddress.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmailAddress.setError("Enter a valid email Address");
            loginEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()){
            loginPassword.setError("Enter Password");
            loginPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            loginPassword.setError("Password Should be at list 6 Character");
            loginPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            gotoHomePage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotoHomePage() {
        Intent intent = new Intent(getApplicationContext(), NavigationDrwareActivity.class);
        startActivity(intent);
    }


}