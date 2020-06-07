package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
        ParseUser.getCurrentUser().logOut();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup:
                if(edtEmail.getText().toString().equals("") ||
                        edtUsername.getText().toString().equals("") ||
                        edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this,
                            "Empty Value is not allowed",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUsername.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signin Up" + edtUsername.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage() + " us signed up",
                                        Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.btnLogin:
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    // Hiding keyboard when tapping on blank area
    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(SignUp.this, SocailMediaActivity.class);
        startActivity(intent);
    }
}