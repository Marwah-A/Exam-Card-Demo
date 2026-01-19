package com.shaqra.examcardv;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class QRcodeActivity extends AppCompatActivity {


    ImageView qrImage;
    Button saveQR;
    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_print);

        qrImage = findViewById(R.id.qr_image);
        saveQR = findViewById(R.id.saveqrbtn);

        qrImage.setImageBitmap(TechrCoursesList.bitmap);


        saveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmapDrawable = (BitmapDrawable) qrImage.getDrawable();
                bitmap = bitmapDrawable.getBitmap();

                FileOutputStream fileOutputStream = null;

                File sdCard = Environment.getExternalStorageDirectory();
                File Directory = new File(sdCard.getAbsoluteFile() + "/Download");
                Directory.mkdir();

                String filename = String.format("%d.png", System.currentTimeMillis());
                File outfile = new File(Directory, filename);

                Toast.makeText(QRcodeActivity.this, "تم الحفظ", Toast.LENGTH_SHORT).show();

                try {
                    fileOutputStream = new FileOutputStream(outfile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();


                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outfile));
                    sendBroadcast(intent);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
