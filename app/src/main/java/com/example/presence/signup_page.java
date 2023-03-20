package com.example.presence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_page extends AppCompatActivity {

    Button signbtn, showpassword, hidepassword;
    EditText sign_name, sign_phonenumber, sign_email, sign_password;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private Boolean validateName(){
        String val = sign_name.getText().toString();

        if(val.isEmpty()){
            sign_name.setError("Field cannot be empty");
            return false;
        }
        else{
            sign_name.setError(null);
            return  true;
        }
    }

    private Boolean validateEmail(){
        String val = sign_email.getText().toString();
        String emailpattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            sign_email.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailpattern)){
            sign_email.setError("Invalid email address");
            return false;
        }
        else{
            sign_email.setError(null);
            return  true;
        }
    }

    private Boolean validatePhonenumber(){
        String val = sign_phonenumber.getText().toString();
        String MobilePattern = "[0-9]{10}";

        if(val.isEmpty()){
            sign_phonenumber.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(MobilePattern)){
            sign_phonenumber.setError("Invalid phone number");
            return false;
        }
        else {
            sign_phonenumber.setError(null);
            return  true;
        }
    }

    private Boolean validatePassword(){
        String val = sign_password.getText().toString();
        String passwordval = "^" +
                "(?=.*[a-zA-Z])" +      //any character
                "(?=.*[@#$%^&+=])" +    //atleast 1 character
                //"\\A\\w{4,20}\\z" +     //no blank spaces
                ".{4,}" +               //atleast 4 character
                "$";

        if(val.isEmpty()){
            sign_password.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordval)){
            sign_password.setError("Password is too weak");
            return false;
        }
        else{
            sign_password.setError(null);
            return  true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);

        sign_name = findViewById(R.id.regName);
        sign_phonenumber = findViewById(R.id.regPhonenumber);
        sign_email = findViewById(R.id.regEmail);
        sign_password = findViewById(R.id.regPassword);
        signbtn = findViewById(R.id.signtohome);
        showpassword = findViewById(R.id.showpassword);
        hidepassword = findViewById(R.id.hidepassword);

        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_password.setTransformationMethod(null);
            }
        });

        hidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                if(!validateName() | !validateEmail() | !validatePassword() | !validatePhonenumber()){
                    return;
                }
                else{
                    Intent intent = new Intent(signup_page.this, Login_page.class);
                    startActivity(intent);
                }

                //Get all the values
                String name = sign_name.getText().toString();
                String phonenumber = sign_phonenumber.getText().toString();
                String email = sign_email.getText().toString();
                String password = sign_password.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, email, phonenumber, password);

                reference.child(phonenumber).setValue(helperClass);
            }
        });

    }
}