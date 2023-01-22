package com.example.test22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText email, username, phone, password;
    Button signup;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signupbtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernames = username.getText().toString().trim();
                final String emails = email.getText().toString().trim();
                final String phones = phone.getText().toString().trim();
                final String pass = password.getText().toString();

                if(usernames.isEmpty() || emails.isEmpty() || phones.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Register.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(usernames)){
                                    Toast.makeText(Register.this, "This username has already been registered", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    reference.child("Users").child(usernames).child("email").setValue(emails);
                                    reference.child("Users").child(usernames).child("phone").setValue(phones);
                                    reference.child("Users").child(usernames).child("password").setValue(pass);
                                    reference.child("Users").child(usernames).child("username").setValue(usernames);
                                    Toast.makeText(Register.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
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