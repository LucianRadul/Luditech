package com.example.databaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {
    private static final String TAG = "LeaderboardActivity";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btnBack;
    private ListView leaderboardLstVw;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Query sortedDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        btnBack = findViewById(R.id.idBtnBackToMain);
        leaderboardLstVw = findViewById(R.id.lstVwLeaderboard);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Players");
        sortedDatabase = databaseReference.orderByChild("score");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderboardActivity.this, MainActivity.class));
            }
        });

        HashMap<String, String> nameAddresses = new HashMap<>();
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});
        leaderboardLstVw.setAdapter(adapter);

//        ValueEventListener databaseListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //arrayList.clear();
//                nameAddresses.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    User test = snapshot.getValue(User.class);
//                    String date = test.getDate();
//                    String day = date.substring(0, 2);
//                    String month = date.substring(2, 4);
//                    String year = date.substring(4,8);
//                    nameAddresses.put(test.getUsername(),
//                            Integer.toString(test.getScore()) + " points" + " on " + day + "/" + month + "/" + year);
//                    Log.d(TAG, "Value is: " + test.toString());
//                }
//
//                Iterator it = nameAddresses.entrySet().iterator();
//                while (it.hasNext())
//                {
//                    HashMap<String, String> resultsMap = new HashMap<>();
//                    Map.Entry pair = (Map.Entry)it.next();
//                    resultsMap.put("First Line", pair.getKey().toString());
//                    resultsMap.put("Second Line", pair.getValue().toString());
//                    listItems.add(resultsMap);
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//
//        //sortedDatabase.addValueEventListener(databaseListener);

        sortedDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                nameAddresses.clear();
                User test = snapshot.getValue(User.class);
                String date = test.getDate();
                String day = date.substring(0, 2);
                String month = date.substring(2, 4);
                String year = date.substring(4,8);
                nameAddresses.put(test.getUsername(),
                        Integer.toString(test.getScore()) + " points" + " on " + day + "/" + month + "/" + year);
                Iterator it = nameAddresses.entrySet().iterator();
                while (it.hasNext())
                {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("First Line", pair.getKey().toString());
                    resultsMap.put("Second Line", pair.getValue().toString());
                    listItems.add(resultsMap);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}