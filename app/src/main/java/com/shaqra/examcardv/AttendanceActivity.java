package com.shaqra.examcardv;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceActivity extends AppCompatActivity {

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    Button allAttendents;

    String courseChildName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        allAttendents = findViewById(R.id.allAttendentbtn);


        switch (TechrCoursesList.childNum) {
            case 1:
                courseChildName = "Programming412";
                break;
            case 2:
                courseChildName = "Databases111";
                break;
            case 3:
                courseChildName = "Information Security222";
                break;
            default:
                break;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("List").child(courseChildName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean attendance;
                String stdID;
                String value;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    stdID = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();
                    attendance = Boolean.parseBoolean(value);

                    if (stdID.equals("123456")) {
                        checkBox1.setChecked(attendance);
                    } else if (stdID.equals("789012")) {
                        checkBox2.setEnabled(attendance);
                    } else if (stdID.equals("112233")) {
                        checkBox3.setChecked(attendance);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean changeAttendance1 = checkBox1.isChecked();
                reference.child("123456").setValue(changeAttendance1);
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean changeAttendance3 = checkBox3.isChecked();
                reference.child("112233").setValue(changeAttendance3);
            }
        });

        allAttendents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("123456").setValue(true);
                reference.child("112233").setValue(true);
            }
        });
    }
}
