package com.t3h.whiyew.appt3h.fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.models.Member;

/**
 * Created by Whiyew on 27/03/2017.
 */
public class MyAbout extends Fragment {
    private EditText adress, age, name, gmail, language;
    ImageView ima;
    boolean check = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infomation, container, false);
        adress = (EditText) v.findViewById(R.id.adress);
        age = (EditText) v.findViewById(R.id.age);
        name = (EditText) v.findViewById(R.id.name);
        gmail = (EditText) v.findViewById(R.id.gmail);
        language = (EditText) v.findViewById(R.id.language);

        adress.setText(Load.member.getAddress());
        age.setText(Load.member.getAge());
        name.setText(Load.member.getName());
        gmail.setText(Load.member.getGmail());
        language.setText(Load.member.getLanguage());

//		myRef.child("Intofmation").child("adress").setValue(adress.getText().toString());
//		myRef.child("Intofmation").child("age").setValue(age.getText().toString());
//		myRef.child("Intofmation").child("name").setValue(name.getText().toString());
//		myRef.child("Intofmation").child("gmail").setValue(gmail.getText().toString());
//		myRef.child("Intofmation").child("language").setValue(language.getText().toString());

        ima = (ImageView) v.findViewById(R.id.image);
        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                DatabaseReference myRef = database.getReference(android_id);
                myRef.child("Intofmation").setValue(new Member(name.getText().toString(),
                        gmail.getText().toString(), age.getText().toString(), language.getText().toString(),
                        adress.getText().toString()));

            }
        });
        return v;
    }



}
