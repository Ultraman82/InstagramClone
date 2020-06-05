package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private Button btnSave, btnGetAkkData;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickspeed, edtKickpower;
    private TextView txtGetData;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.button);
        btnSave.setOnClickListener(SignUp.this);
        btnGetAkkData = findViewById(R.id.btnGetKickers);


        edtName = findViewById(R.id.edtName);
        edtKickpower = findViewById(R.id.edtkickpower);
        edtKickspeed = findViewById(R.id.edtkickspeed);
        edtPunchPower = findViewById(R.id.edtpunchpower);
        edtPunchSpeed = findViewById(R.id.edtpunchspeed);
        txtGetData = findViewById(R.id.txtGetData);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("kicker");
                parseQuery.getInBackground("a3PcfKPfKC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(object.get("name").toString());
                        }
                    }
                });
            }
        });

        btnGetAkkData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("kicker");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if ( e == null ) {
                            if (objects.size() > 0) {
                                for(ParseObject parseObject: objects) {
                                    allKickBoxers = allKickBoxers + parseObject.get("name") +"\n";
                                }

                                FancyToast.makeText(SignUp.this, allKickBoxers,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {



        final ParseObject kickBoxer = new ParseObject("kicker");
        kickBoxer.put("name", edtName.getText().toString());
        kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
        kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
        kickBoxer.put("kickSpeed", Integer.parseInt(edtKickspeed.getText().toString()));
        kickBoxer.put("kickPower", Integer.parseInt(edtKickpower.getText().toString()));

        try {
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        System.out.println("Success");
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " has been created",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                    } else {
                        System.out.println("Error");

                    }
                }
            });
        } catch (Exception e ) {
            FancyToast.makeText(SignUp.this, e.getMessage() ,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }


    }
}

//    @Override
//    public void onClick(View v) {
//        final ParseObject kickBoxer = new ParseObject("Kicker");
//        kickBoxer.put("name", edtName.getText().toString());
//        kickBoxer.put("punchSpeed", edtPunchSpeed.getText().toString());
//        kickBoxer.put("punchPower", edtPunchPower.getText().toString() );
//        kickBoxer.put("kickSpeed", edtKickspeed.getText().toString());
//        kickBoxer.put("kickPower", edtKickpower.getText().toString());
//        @Override
//        public void done(ParseException e) {
//            Toast.makeText(SignUp.this, kickBoxer.get("name").toString(), Toast.LENGTH_SHORT).show();
//        }
//    }





//        try {
//            ParseUser user = new ParseUser();
//            user.setUsername("Edgar");
//            user.setPassword("1234");
//            user.setEmail("exelcior99@gmail.com");
//
//            user.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        ParseUser.logOut();
//                        System.out.println("Login Successful");
//
//                    } else {
//                        ParseUser.logOut();
//                        System.out.println("Login failed");
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//    private void alertDisplayer(String title, String message, final boolean error) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        if (!error) {
//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    }
//                });
//        AlertDialog ok = builder.create();
//        ok.show();
//    }
