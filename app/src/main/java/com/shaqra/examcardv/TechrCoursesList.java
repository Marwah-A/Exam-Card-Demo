package com.shaqra.examcardv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class TechrCoursesList extends AppCompatActivity {

    Button listButton1;
    Button listButton2;
    Button listButton3;

    Button codeButton1;
    Button codeButton2;
    Button codeButton3;

    TextView courseTextView1;
    TextView courseTextView2;
    TextView courseTextView3;

    static Bitmap bitmap;

    String theCourseName;
    static int childNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techrcourses_list);

        listButton1 = findViewById(R.id.listbtn1);
        listButton2 = findViewById(R.id.listbtn2);
        listButton3 = findViewById(R.id.listbtn3);

        codeButton1 = findViewById(R.id.codebtn1);
        codeButton2 = findViewById(R.id.codebtn2);
        codeButton3 = findViewById(R.id.codebtn3);

        courseTextView1 = findViewById(R.id.courseTV1);
        courseTextView2 = findViewById(R.id.courseTV2);
        courseTextView3 = findViewById(R.id.courseTV3);

/////////////////////////
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child("teacher").child(LoginActivity.theUserId).child("course");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                String value;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    key = dataSnapshot.getKey();
                    value = dataSnapshot.getValue().toString();
                    if (key.equals("Programming412")){
                        courseTextView1.setText(value);
                    } else if (key.equals("Databases111")){
                        courseTextView2.setText(value);
                    } else if (key.equals("Information Security222")){
                        courseTextView3.setText(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
////////////////////////

        listButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView1.getText().toString();
                childNum = 1;
                Intent intent = new Intent(TechrCoursesList.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        listButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView2.getText().toString();
                childNum = 2;
                Intent intent = new Intent(TechrCoursesList.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        listButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView3.getText().toString();
                childNum = 3;
                Intent intent = new Intent(TechrCoursesList.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        codeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView1.getText().toString();
                bitmap = generateQRcode(getCourseCode(theCourseName));
                Intent intent = new Intent(TechrCoursesList.this, QRcodeActivity.class);
                startActivity(intent);
            }
        });

        codeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView2.getText().toString();
                bitmap = generateQRcode(getCourseCode(theCourseName));
                Intent intent = new Intent(TechrCoursesList.this, QRcodeActivity.class);
                startActivity(intent);
            }
        });

        codeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theCourseName = courseTextView3.getText().toString();
                bitmap = generateQRcode(getCourseCode(theCourseName));
                Intent intent = new Intent(TechrCoursesList.this, QRcodeActivity.class);
                startActivity(intent);
            }
        });
    }


    public Bitmap generateQRcode(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Bitmap bitmap1 = null;

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            bitmap1 = bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap1;
    }

    public String getCourseCode(String courseName) {
        switch (courseName) {
            case "لغة برمجة1":
                return "Programming412";
            case "قواعد بيانات":
                return "Databases111";
            case "أمن المعلومات":
                return "Information Security222";
        }
        return "";
    }
}
