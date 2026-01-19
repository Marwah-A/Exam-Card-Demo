package com.shaqra.examcardv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamEntryCard extends AppCompatActivity {

    private static final String TAG = "mainACTIVITY";
    static String scannedText;
    static boolean flag;
    static String theCourseCode;
    static String stdStatus;
    TextView studentName;
    TextView studentID;
    TextView studentStatus;
    TextView studentCollege;
    TextView studentMajor;
    TextView studentLevel;
    TextView courseName;
    TextView sectionID;
    TextView examDay;
    TextView examDate;
    TextView examTime;
    TextView examPeriod;
    TextView examHall;
    Button scanButton;
    ///////////
    String takeId = LoginActivity.theUserId.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_card);

        //student
        studentName = findViewById(R.id.std_name);
        studentID = findViewById(R.id.std_id);
        studentStatus = findViewById(R.id.std_status);
        studentCollege = findViewById(R.id.std_college);
        studentMajor = findViewById(R.id.std_major);
        studentLevel = findViewById(R.id.std_level);
        //exam
        courseName = findViewById(R.id.course_name);
        sectionID = findViewById(R.id.section_id);
        examDay = findViewById(R.id.exam_day);
        examDate = findViewById(R.id.exam_date);
        examTime = findViewById(R.id.exam_time);
        examPeriod = findViewById(R.id.exam_period);
        examHall = findViewById(R.id.exam_hall);
        //scan button
        scanButton = findViewById(R.id.scanbtn);

        //call methods
        setStdInfo();
        checkDateAndTime();


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ExamEntryCard.this);
                //intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("scan qr code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                scannedText = intentResult.getContents();
                if (scannedText.equals(theCourseCode)) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("List").child(theCourseCode).child(LoginActivity.theUserId);
                    reference.setValue(true);
                    Intent intent = new Intent(ExamEntryCard.this, SuccessfulScan.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ExamEntryCard.this, FailedScan.class);
                    startActivity(intent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to compare the exam date and time to the current date and time of the system
    public void checkDateAndTime() {

        String[] examDates = {"06/06", "06/07"};
        String dateFormat = "MM/dd";
        SimpleDateFormat SDF = new SimpleDateFormat(dateFormat);
        Date dateOfDay = new Date();
        String dateOfDayFormated = SDF.format(dateOfDay);

        String[] examTimes = {"07:45", "09:45", "11:45"};
        String timeFormat = "HH/mm";
        SimpleDateFormat SDF2 = new SimpleDateFormat(timeFormat);
        Date timeOfDay = new Date();
        String timeOfDayFormated = SDF2.format(timeOfDay);

        for (int i = 0; i < examDates.length; i++) {
            if (dateOfDayFormated.equals(examDates[i])) {
                for (int j = 0; j < examTimes.length; j++) {
                    String replaceExamTime = examTimes[j].replace(":", "");
                    String replaceDayTime = timeOfDayFormated.replace("/", "");
                    int examTimeInt = Integer.parseInt(replaceExamTime);
                    int dayTimeInt = Integer.parseInt(replaceDayTime);

                    if (dayTimeInt >= examTimeInt && dayTimeInt < examTimeInt + 200) {
                        String result = examDates[i] + " p" + ++j;
                        specifyExam(result);
                        break;
                    }
                }
            }
        }
    }

    //method to determine which exam info to display based on the date and period
    public void specifyExam(String examDateAndPeriod) {
        switch (examDateAndPeriod) {
            case "06/06 p1":
            case "06/07 p1":
                theCourseCode = "Programming412";
                setExamInfo();
                break;
            case "06/06 p2":
            case "06/07 p2":
                theCourseCode = "Databases111";
                setExamInfo();
                break;
            case "06/06 p3":
            case "06/07 p3":
                theCourseCode = "Information Security222";
                setExamInfo();
                break;
            default:
                break;
        }
    }

    public void setStdInfo() {

        studentID.setText(LoginActivity.theUserId);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child("student").child(LoginActivity.theUserId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                String value;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    key = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();

                    if (key.equals("name")) {
                        studentName.setText(value);
                    } else if (key.equals("status")) {
                        studentStatus.setText(value);
                        if (value.equals("منتظم")) {
                            stdStatus = "منتظم";
                            studentStatus.setTextColor(Color.GREEN);
                        } else if (value.equals("محروم")) {
                            stdStatus = "محروم";
                            studentStatus.setTextColor(Color.RED);
                        }
                    } else if (key.equals("college")) {
                        studentCollege.setText(value);
                    } else if (key.equals("major")) {
                        studentMajor.setText(value);
                    } else if (key.equals("level")) {
                        studentLevel.setText(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setExamInfo() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("exam").child(theCourseCode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                String value;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    key = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();

                    if (key.equals("name")) {
                        courseName.setText(value);
                    } else if (key.equals("section")) {
                        sectionID.setText(value);
                    } else if (key.equals("day")) {
                        examDay.setText(value);
                    } else if (key.equals("date")) {
                        examDate.setText(value);
                    } else if (key.equals("time")) {
                        examTime.setText(value);
                    } else if (key.equals("period")) {
                        examPeriod.setText(value);
                    } else if (key.equals("hall")) {
                        examHall.setText(value);
                    }
                }
                if (studentStatus.getText().toString().equals("منتظم")) {
                    System.out.println("this part works");
                    scanButton.setEnabled(true);
                    scanButton.setBackgroundColor(getColor(R.color.uni_green));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}