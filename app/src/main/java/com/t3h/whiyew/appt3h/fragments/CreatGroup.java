package com.t3h.whiyew.appt3h.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.Group;

import java.util.Random;

/**
 * Created by Whiyew on 26/03/2017.
 */

public class CreatGroup extends Fragment {
    Button Creat;
    EditText Name, Access, Language;
    ProgressDialog progress;
    ProgressBar bar;
    RelativeLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.creat_namegroup, container, false);

        Name = (EditText) v.findViewById(R.id.name);
        Access = (EditText) v.findViewById(R.id.access);
        Language = (EditText) v.findViewById(R.id.language);

        Creat = (Button) v.findViewById(R.id.creat);
        bar=(ProgressBar)v.findViewById(R.id.progressBar) ;
        relativeLayout=(RelativeLayout)v.findViewById(R.id.rl);
        Creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                progress = ProgressDialog.show(getActivity(), "Creating",
//                        "Please wait!", true);
                try {
                    My thread = new My(getActivity());
                    thread.execute();
                } catch (Exception e) {
                    My thread = new My(getActivity());
                    thread.execute();
                }

            }
        });
        return v;
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    class My extends AsyncTask<Void, Integer, Void> {
        GridView mGridView;
        Activity mContex;
        String nameid = Name.getText().toString();
        String language = Language.getText().toString();
        String access = Access.getText().toString();
        boolean check1 = true, check2 = true, check3 = true;

        boolean temp = true;

        public My(Activity contex) {

            this.mContex = contex;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            relativeLayout.setVisibility(View.VISIBLE);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String name = nameid + " " + android_id + " " + CreatGroup.random();
            Group group = new Group(name, language, access);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(android_id);
            DatabaseReference allGroup = database.getReference("Groups");
            allGroup.child(name).child("InforGroup").setValue(group);
            allGroup.child(name).child("InforGroup").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    check2 = false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            allGroup.child(name).child("NameOfGroup").push().setValue(android_id);
            allGroup.child(name).child("NameOfGroup").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    check3 = false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            myRef.child("Group").push().setValue(group);
            myRef.child("Group").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    check1 = false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            while (temp == true) {
                try {
                    Thread.sleep(500);
                    if (check1 == false && check2 == false && check3 == false) {

                        break;
                    }
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            relativeLayout.setVisibility(View.GONE);
            bar.setVisibility(View.GONE);
//            progress.dismiss();
            Toast.makeText(getActivity(), "You are Creat a group! ", Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new ManagerGroup());
            fragmentTransaction.commit();
        }
    }
}
