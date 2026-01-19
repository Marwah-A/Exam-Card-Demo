package com.shaqra.examcardv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StdASectionActivity extends AppCompatActivity {

    CardView cardIcon;
    TextView studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_academic_section);

        studentName = findViewById(R.id.std_name);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child("student").child(LoginActivity.theUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                String value;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    key = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();
                    if (key.equals("name")){
                        studentName.setText(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardIcon = findViewById(R.id.entrycard_cardview);
        cardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StdASectionActivity.this, ExamEntryCard.class);
                startActivity(intent);
            }
        });
    }
}