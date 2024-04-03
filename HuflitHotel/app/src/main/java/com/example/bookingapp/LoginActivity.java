package com.example.bookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {
    Button btLogin, btRegister;
    EditText edUserNameC, edPasswordC;
    SharedPreferences.Editor editor;
    private final Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        //
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btCancel);
        edUserNameC = findViewById(R.id.LoginUserName);
        edPasswordC = findViewById(R.id.LoginPassword);
        //
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
//        btLogin.setOnClickListener(nhanvaoLogin());
//        btRegister.setOnClickListener(nhanvaoRegister());
        //
    }

    private void register(){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }
    private void login(){
        String email,pass;
        email= edUserNameC.getText().toString();
        pass= edPasswordC.getText().toString();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.equals("admin@gmail.com") && pass.equals("admin1")){
            Toast.makeText(getApplicationContext(), "Chào mừng admin", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this,RoomActivity.class);
            startActivity(i);
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}