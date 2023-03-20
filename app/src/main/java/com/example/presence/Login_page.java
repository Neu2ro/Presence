package com.example.presence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {

    public Button button1, showpassword, hidepassword, btn2;
    EditText login_phone, login_Password;

    private Boolean validatePhonenumber(){
        String val = login_phone.getText().toString();
        String MobilePattern = "[0-9]{10}";

        if(val.isEmpty()){
            login_phone.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(MobilePattern)){
            login_phone.setError("Invalid phone number");
            return false;
        }
        else {
            login_phone.setError(null);
            return  true;
        }
    }

    private Boolean validatePassword(){
        String val = login_Password.getText().toString();
        if(val.isEmpty()){
            login_Password.setError("Field cannot be empty");
            return false;
        }
        else{
            login_Password.setError(null);
            return  true;
        }
    }

    private void isUser() {

        String userEnteredPhoneNo = login_phone.getText().toString().trim();
        String userEnteredPassword = login_Password.getText().toString().trim();

        DatabaseReference node = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = node.orderByChild("phoneNo").equalTo(userEnteredPhoneNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    login_phone.setError(null);

                    String passwordfromDB = snapshot.child(userEnteredPhoneNo).child("password").getValue(String.class);

                    assert passwordfromDB != null;
                    if(passwordfromDB.equals(userEnteredPassword)){

                        login_Password.setError(null);

                        String usernamefromDB = snapshot.child(userEnteredPhoneNo).child("phoneNo").getValue(String.class);

                        Intent intent = new Intent(Login_page.this, Dashboard.class);
                        intent.putExtra("key",userEnteredPhoneNo);
                        startActivity(intent);

                    }
                    else{
                        login_Password.setError("Wrong Password");
                        login_Password.requestFocus();
                    }
                }
                else {
                    login_phone.setError("No such user exists");
                    login_phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        button1 = (Button) findViewById(R.id.login);
        showpassword = findViewById(R.id.showpassword);
        hidepassword = findViewById(R.id.hidepassword);
        login_Password = findViewById(R.id.loginpassword);
        login_phone = findViewById(R.id.loginphoneNo);
        btn2 = findViewById(R.id.signup);

        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_Password.setTransformationMethod(null);
            }
        });

        hidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_Password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_page.this, Dashboard.class);
                startActivity(intent);
//                if(!validatePhonenumber() | !validatePassword()) {
//                    return;
//                }
//                else{
//                    isUser();
//                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_page.this, signup_page.class);
                startActivity(intent);
            }
        });
    }
}