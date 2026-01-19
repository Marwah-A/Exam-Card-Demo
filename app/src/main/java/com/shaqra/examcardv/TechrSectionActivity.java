package com.shaqra.examcardv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TechrSectionActivity extends AppCompatActivity {

    TextView teacherName;
    CardView listIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techr_academic_section);

        teacherName = findViewById(R.id.techr_name);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child("teacher").child(LoginActivity.theUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                String value;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    key = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();
                    if (key.equals("name")){
                        teacherName.setText(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        listIcon = findViewById(R.id.attendancelist_cardview);
        listIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechrSectionActivity.this, TechrCoursesList.class);
                startActivity(intent);
            }
        });
    }
}
