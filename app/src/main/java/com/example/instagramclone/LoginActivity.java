package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoginActivity, btnSignupLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edtEmailLogin);
        edtLoginPassword = findViewById(R.id.edtPasswordLogin);
        btnLoginActivity = findViewById(R.id.btnLoginLogin);
        btnSignupLogin = findViewById(R.id.btnSignupLogin);

        btnLoginActivity.setOnClickListener(this);
        btnSignupLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginLogin:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    FancyToast.makeText(LoginActivity.this, user.getUsername() + " is logged in", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    transitionToSocialMediaActivity();
                                } else {
                                    FancyToast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            }
                        });
                break;
            case R.id.btnSignupLogin:
                break;
        }
    }
    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocailMediaActivity.class);
        startActivity(intent);
    }
}