package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileFavSport = view.findViewById(R.id.edtProfileSport);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);

        btnUpdateInfo = view.findViewById(R.id.btnProfileUpdate);

        final ParseUser parseUser = ParseUser.getCurrentUser();


//        edtProfileName.setText(parseUser.get("profileName") + "");
//        edtProfileBio.setText(parseUser.get("profileBio") + "");
//        edtProfileFavSport.setText(parseUser.get("profileFavSport") + "");
//        edtProfileHobbies.setText(parseUser.get("profileHobbies") + "");
//        edtProfileProfession.setText(parseUser.get("profileProfession") + "");


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileFavSport", edtProfileFavSport.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), parseUser.getUsername() + "'s info is updated",
                                    Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage() + " us signed up",
                                    Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }
}