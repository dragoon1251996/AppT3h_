package com.t3h.whiyew.appt3h.fragments;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.adapters.LvMemberAdapter;
import com.t3h.whiyew.appt3h.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiyew on 29/03/2017.
 */

public class FragmentGroup extends Fragment  {
    LvMemberAdapter lvMemberAdapter;
    ListView listView;
    List<Member> arraylist;
    ArrayList<String> idMember;
    LinearLayout linearLayout;
    boolean aBoolean = true;
    int temp;
    public static ArrayList<String> ID = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_option, container, false);
        ManagerGroup.checker = true;


        linearLayout = (LinearLayout) v.findViewById(R.id.line1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_content, new AddMember());
//                fragmentTransaction.commit();
//                String[] title = ManagerGroup.nameOfGroupClick.split(" ");
//                getActivity().setTitle("Add member");
//                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                new My(getActivity()).execute();
            }
        });
        listView = (ListView) v.findViewById(R.id.lv_group);
        arraylist = new ArrayList<>();
        idMember = new ArrayList<>();

        lvMemberAdapter = new LvMemberAdapter(getActivity(), R.layout.item_group, arraylist);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, idMember);


        listView.setAdapter(lvMemberAdapter);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        for (int i = 0; i < ManagerGroup.arrayId.size(); i++) {
            DatabaseReference myRef = database.getReference(ManagerGroup.arrayId.get(i));
            temp=i;
            myRef.child("Intofmation").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Member member;
                    if (dataSnapshot.getValue(Member.class) == null) {
                        member = new Member(FragmentGroup.ID.get(temp), FragmentGroup.ID.get(temp) + ".com", "0", "English", "Việt Nam");
                    } else {
                        member = dataSnapshot.getValue(Member.class);
                    }
                    arraylist.add(member);
                    lvMemberAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return v;
    }


    class My extends AsyncTask<Void, Integer, Void> {
        GridView mGridView;
        Activity mContex;

        public My(Activity contex) {

            this.mContex = contex;
        }

        @Override
        protected Void doInBackground(Void... params) {
            boolean cher = true;
            ID=new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals("Groups") == false) {
                        ID.add(dataSnapshot.getKey());
                    }
                    aBoolean = false;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            while (cher == true) {
                try {
                    Thread.sleep(500);
                    if (aBoolean == false) {
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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new AddMember());
            fragmentTransaction.commit();
            String[] title = ManagerGroup.nameOfGroupClick.split(" ");
            getActivity().setTitle("Add member");
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
