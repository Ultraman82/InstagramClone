package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersTab extends Fragment {

    private ListView mListView;
    private ArrayList mArrayList;
    private ArrayAdapter mArrayAdapter;
    //private TextView txtLoadingUsers;
        public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        mListView = view.findViewById(R.id.listview);
        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mArrayList);

        //final TextView txtLoadingUsers = getActivity().findViewById(R.id.txtGetData);
        final TextView txtLoadingUsers = view.findViewById(R.id.txtLoadingUsers);

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Updating");
//        progressDialog.show();

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();


        //parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().get("username"));

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    if(users.size() > 0) {
                        for(ParseUser user : users) {
                            mArrayList.add(user.getUsername());
                        }
                        mListView.setAdapter(mArrayAdapter);
                        txtLoadingUsers.animate().alpha(0).setDuration(2000);
                        mListView.setVisibility(View.VISIBLE);
                    } else {

                    }
                } else {

                }
                //progressDialog.dismiss();
            }
        });
        return view;
    }
}