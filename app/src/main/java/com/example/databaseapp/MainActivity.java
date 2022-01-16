package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button addData;
    private Button leaderboardBtn;
    private Button logoutBtn;
    private EditText nameField;
    private EditText scoreField;
    private EditText dateField;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData = findViewById(R.id.idBtnAddData);
        leaderboardBtn = findViewById(R.id.idBtnLeaderboard);
        nameField = findViewById(R.id.EdtTxtName);
        scoreField = findViewById(R.id.EdtTxtScore);
        dateField = findViewById(R.id.EdtTxtDate);
        logoutBtn = findViewById(R.id.idBtnLogout);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Players");

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = nameField.getText().toString();
                String scoreStr = scoreField.getText().toString();
                String dateString = dateField.getText().toString();
                int scoreString = Integer.parseInt(scoreStr);

                writeNewUser(nameString, nameString, scoreString, dateString);
                Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
            }
        });

        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                System.exit(0);
            }
        });
    }

    public void writeNewUser(String userId, String name, int score, String date) {
        User user = new User(name, score, date);
        databaseReference.child(userId).setValue(user);
    }
}