package com.shaqra.examcardv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginManager loginManager;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;


    public static String theUserId;
    public static String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginManager = new LoginManager(getApplicationContext());

        usernameEditText = findViewById(R.id.username_editText);
        passwordEditText = findViewById(R.id.password_editText);
        loginButton = findViewById(R.id.login_button);

        final long SCREEN_DELAY = 1000;


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean loginSuccess = loginManager.login(username, password);

                if (loginSuccess) {
                    Toast.makeText(LoginActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                    if (username.equals("778899") || username.equals("445566")){
                        role = "teacher";
                    }else {
                        role = "student";
                    }
                    theUserId = username;

                    // استخدم معالج لتأخير تحميل شاشة الدخول للشاشة الجديدة
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (role.equals("teacher")) {
                                Intent intent = new Intent(LoginActivity.this, TechrSectionActivity.class);
                                startActivity(intent);

                            } else if (role.equals("student")){
                                Intent intent = new Intent(LoginActivity.this, StdASectionActivity.class);
                                startActivity(intent);
                            }
                        }

                    }, SCREEN_DELAY);
                } else {
                    Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
