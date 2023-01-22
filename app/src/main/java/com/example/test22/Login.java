package com.example.test22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button signin;
    TextView signuptext;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signuptext = findViewById(R.id.signuptext);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signinBtn);

        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getApplicationContext(), Register.class);
                startActivity(signup);

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernames = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(usernames.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                    password.requestFocus();
                }
                else {
                    reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernames)){
                                final String getPassword = snapshot.child(usernames).child("password").getValue(String.class);
                                if(getPassword.equals(pass)){
                                  //  final String uuser = snapshot.child(usernames).child("username").getValue(String.class);
                                    Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                 //   main.putExtra("username", uuser);
                                    startActivity(main);
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}