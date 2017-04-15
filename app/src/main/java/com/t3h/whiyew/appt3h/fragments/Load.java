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
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.R;
import com.t3h.whiyew.appt3h.activity.MainActivity;
import com.t3h.whiyew.appt3h.models.Group;
import com.t3h.whiyew.appt3h.models.Member;

import java.util.ArrayList;

/**
 * Created by Whiyew on 28/03/2017.
 */

public class Load extends Fragment {
    public static Member member = new Member("", "", "", "", "");
    public static ArrayList<String> allgroupOfMe = new ArrayList<>();
    boolean check = true;
    boolean check1 = true;
    boolean check3 = true;




    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.load, container, false);


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        DatabaseReference myRef = database.getReference(android_id);
//        myRef.child("Group").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Toast.makeText(getContext(),dataSnapshot.getChildrenCount()+"",Toast.LENGTH_LONG).show();
//                MainActivity.numberOfGroup = (int) dataSnapshot.getChildrenCount();
//               if (MainActivity.numberOfGroup != 0) {
//                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();                 fragmentTransaction.replace(R.id.main_content, new MyHome());
//                   fragmentTransaction.commit();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        new My(getActivity()).execute();
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
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            final DatabaseReference myRef = database.getReference(android_id);
            myRef.child("Group").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()==null){check=false;}
                    MainActivity.numberOfGroup = (int) dataSnapshot.getChildrenCount();
                    check3 = false;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.child("Group").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    MainActivity.numberOfGroup = (int) dataSnapshot.getChildrenCount();
                    if (dataSnapshot.getValue(Member.class) == null) {
                        check = false;
                    } else {
                        allgroupOfMe.add(dataSnapshot.getValue(Group.class).getName().toString());
                        check = false;
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue(Member.class) == null) {
                        check = false;
                    } else {
                        allgroupOfMe.add(dataSnapshot.getValue(Group.class).getName().toString());
                        check = false;
                    }
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
            myRef.child("Intofmation").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(Member.class) == null) {
                        member = new Member(android_id, android_id + ".com", "0", "English", "Việt Nam");
                        myRef.child("Intofmation").setValue(member);
                    } else {
                        member = dataSnapshot.getValue(Member.class);
                    }
                    check1 = false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            while (cher == true) {
                try {
                    Thread.sleep(500);
                    if (check == false && check1 == false && check3 == false) {
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
            if (check == false && check1 == false && check3 == false) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, new ManagerGroup());
                fragmentTransaction.commit();

            }
        }
    }

//    class My1 extends AsyncTask<Void, Integer, Void> {
//        GridView mGridView;
//        Activity mContex;
//        int temp;
//
//        public My1(Activity contex) {
//
//            this.mContex = contex;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            boolean cher = true;
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//            DatabaseReference myRef = database.getReference("Groups");
//
//                myRef.child(Load.allgroupOfMe.get(i)).child("NameOfGroup").addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        idMember.get(temp).add(dataSnapshot.getValue().toString());
//                        check4 = false;
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            while (cher == true) {
//                try {
//                    Thread.sleep(500);
//                    if (check4 == false) {
//                        break;
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (check4 == false) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_content, new MyHome());
//                fragmentTransaction.commit();
//            }
//        }
//    }


}
