package com.example.mycontacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_main2 extends AppCompatActivity {

    private Button btSignIn;
    private Button btSignUp;
    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        btSignIn = findViewById(R.id.btSignIn);
        btSignUp = findViewById(R.id.btSignUp);

        edtEmail = findViewById(R.id.emailinput);
        edtPassword = findViewById(R.id.passwordinput);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        // xử lý phần đăng ký khi người dùng phải nhập đầy đủ thông tin mới đăng ký được
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    dbHelper.addUser(new User(edtEmail.getText().toString(), edtPassword.getText().toString()));
                    Toast.makeText(activity_main2.this, "Người Dùng Đã Thêm", Toast.LENGTH_SHORT).show();
                    edtEmail.setText("");
                    edtPassword.setText("");
                }else{
                    Toast.makeText(activity_main2.this, "Mời bạn nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xử lý nút lệnh đăng nhập kiểm tra xem người dùng đã nhập đầy đủ thông tin chưa buộc người dừng đăng ký hoặc là điền đúng thông tin sẽ cho vào trang chính
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    User user = dbHelper.queryUser(edtEmail.getText().toString(), edtPassword.getText().toString());
                    if (user != null) {
                        Bundle mBundle = new Bundle();
                        mBundle.putString("user", user.getEmail());
                        Intent intent = new Intent(activity_main2.this, MainActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        Toast.makeText(activity_main2.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_main2.this, "User not found", Toast.LENGTH_SHORT).show();
                        edtPassword.setText("");
                    }
                }else{
                    Toast.makeText(activity_main2.this, "Người dùng không tồn tại Mời bạn đăng ký", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}
