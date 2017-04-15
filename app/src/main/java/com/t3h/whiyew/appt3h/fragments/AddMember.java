package com.t3h.whiyew.appt3h.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.adapters.LvMemberAdd;
import com.t3h.whiyew.appt3h.models.Member;

import java.util.ArrayList;

/**
 * Created by Whiyew on 29/03/2017.
 */

public class AddMember extends Fragment {
    AutoCompleteTextView autoCompleteTextView;
    boolean aBoolean = true;
    Button button;
    int temp;
    ArrayList<Member> members;
    ArrayList<String> name;
    LvMemberAdd lvMemberAdd;
    ListView listView;
private ArrayList<String>arr(ArrayList<String>A,ArrayList<String>B){
    ArrayList<String>C=new ArrayList<>();

        for(int j=0;j<A.size();j++){
            for(int i=0;i<B.size();i++){

                if(A.get(j).equals(B.get(i))==true){
                    A.remove(j);
                }

            }
        }
    return A;
}
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addmember, container, false);
        autoCompleteTextView = (AutoCompleteTextView) v.findViewById(R.id.text);
        name = new ArrayList<>();
        name.add("ok");
        members = new ArrayList<>();
        button = (Button) v.findViewById(R.id.button1);

        FragmentGroup.ID=arr(FragmentGroup.ID,ManagerGroup.arrayId);

        listView = (ListView) v.findViewById(R.id.lv_group);
        members = new ArrayList<>();
        lvMemberAdd = new LvMemberAdd(getContext(), R.layout.item_memberadd, members);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, name);
        listView.setAdapter(lvMemberAdd);

        //  new My(getActivity()).execute();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (int i = 0; i < FragmentGroup.ID.size(); i++) {
            temp = i;
            DatabaseReference myRef = database.getReference(FragmentGroup.ID.get(i));
            myRef.child("Intofmation").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Member member;
                    if (dataSnapshot.getValue(Member.class) == null) {
                        member = new Member(FragmentGroup.ID.get(temp), FragmentGroup.ID.get(temp) + ".com", "0", "English", "Việt Nam");
                    } else {
                        member = dataSnapshot.getValue(Member.class);
                    }
                    members.add(member);
                    lvMemberAdd.notifyDataSetChanged();
                    //   Toast.makeText(getContext(),dataSnapshot.getValue(Member.class).getGmail(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        return v;
    }


}
