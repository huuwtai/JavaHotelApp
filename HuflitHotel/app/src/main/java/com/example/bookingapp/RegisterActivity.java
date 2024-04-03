package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.Database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText edUserNameC;
    private EditText edPassWordC;
    private EditText edConfirmPassWordC;
    private EditText edfullname;
    private EditText edPhoneNumberC;
    private RadioGroup rbSex;
    private Button btnRegister;
    private ImageButton imbBack;
    private FirebaseAuth    mAuth;
    private FirebaseDatabase mData;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //lay du lieu
        anhxadulieu();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        imbBack.setOnClickListener(v->finish());
    }
    void anhxadulieu(){
        edUserNameC = findViewById(R.id.edUserNameRe);
        edPassWordC = findViewById(R.id.edPasswordRe);
        edConfirmPassWordC = findViewById(R.id.edt_confirm_password);
        edPhoneNumberC = findViewById(R.id.edPhone);
        edfullname = findViewById(R.id.fullname);
        rbSex = findViewById(R.id.rgSex);
        btnRegister = findViewById(R.id.btRegister);
        imbBack = findViewById(R.id.imbBack);
    }
    //
    private void register(){
        String username = edUserNameC.getText().toString();
        String password = edPassWordC.getText().toString();
        String confirmpass = edConfirmPassWordC.getText().toString();
        String phone = edPhoneNumberC.getText().toString();
        String fullname = edfullname.getText().toString();
          String sex;
        int sexselect = rbSex.getCheckedRadioButtonId();
        if (sexselect == R.id.rbFemale)
        {
            sex = "Nữ";
        }
        else {sex= "Nam";}

        //database build
        mData = FirebaseDatabase.getInstance();
        reference = mData.getReference("Users");
        User user = new User(username,phone,sex,password,fullname);
        reference.child(phone).setValue(user);
        //
        if (phone.isEmpty()){
            edPhoneNumberC.setError("Số điện thoại không được để trống");
            edPhoneNumberC.requestFocus();
            return;
        }
        if (fullname.isEmpty()){
            edfullname.setError("Họ tên không được để trống");
            edfullname.requestFocus();
            return;
        }
        if (username.isEmpty()){
            edUserNameC.setError("Email không được để trống");
            edUserNameC.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            edUserNameC.setError("Vui lòng nhập Email đúng");
            edUserNameC.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edPassWordC.setError("Password không được để trống");
            edPassWordC.requestFocus();
            return;

        }
        if(password.length() <=5) {
            edPassWordC.setError("Mật khẩu phải dài 6 kí tự");
            edPassWordC.requestFocus();
            return;
        }
        if(!password.equals(confirmpass)){
            edConfirmPassWordC.setError("Xác nhận mật khẩu không trùng khớp");
            edConfirmPassWordC.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Tạo người dùng thành công!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(RegisterActivity.this,"Tạo người dùng không thành công!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}