package com.ai.game.instashop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public EditText name, eMail, password, confirmPassword;
    public ImageView togglePasswordVisibility;
    public ImageView togglePasswordVisibility2;
    public Boolean passwordVisible = false;
    public Boolean passwordVisible2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextTextPersonName);
        eMail = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        togglePasswordVisibility = findViewById(R.id.passwordVisibility);
        togglePasswordVisibility2 = findViewById(R.id.passwordVisibility2);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().length() != 0){
                    togglePasswordVisibility.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (confirmPassword.getText().length() != 0){
                    togglePasswordVisibility2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void logIn(View view){
        finish();
    }

    public void signUp(View view){
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(eMail.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(confirmPassword.getText())) {
            if(TextUtils.isEmpty(eMail.getText())) eMail.setError("Email-Id Required");
            if(TextUtils.isEmpty(name.getText())) name.setError("Name Required");
            if(TextUtils.isEmpty(password.getText())) {
                password.setError("Password Required");
                togglePasswordVisibility.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(confirmPassword.getText())) {
                confirmPassword.setError("Confirm Password Required");
                togglePasswordVisibility2.setVisibility(View.INVISIBLE);
            }
        }
        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
            Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();

        else{
            ParseUser user = new ParseUser();
            user.setUsername(eMail.getText().toString().trim());
            user.setEmail(eMail.getText().toString().trim());
            user.setPassword(password.getText().toString());
            user.put("Name", name.getText().toString().trim());
            user.saveInBackground();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toast.makeText(getApplicationContext(), "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ParseUser.logOut();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void togglePasswordVisibility(View v) {
        if(!passwordVisible) {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            togglePasswordVisibility.setImageResource(R.drawable.ic_password_visible);
            password.setSelection(password.getText().length());
            passwordVisible = true;
        }
        else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            togglePasswordVisibility.setImageResource(R.drawable.ic_password_invisible);
            password.setSelection(password.getText().length());
            passwordVisible = false;
        }
    }
    public void togglePasswordVisibility2(View v) {
        if(!passwordVisible2) {
            confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            togglePasswordVisibility2.setImageResource(R.drawable.ic_password_visible);
            confirmPassword.setSelection(confirmPassword.getText().length());
            passwordVisible2 = true;
        }
        else {
            confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            togglePasswordVisibility2.setImageResource(R.drawable.ic_password_invisible);
            confirmPassword.setSelection(confirmPassword.getText().length());
            passwordVisible2 = false;
        }
    }
}