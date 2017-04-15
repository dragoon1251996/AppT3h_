package com.t3h.whiyew.appt3h.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Settings;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t3h.whiyew.appt3h.abstractclass.WakeLocker;
import com.t3h.whiyew.appt3h.activity.Warning;

/**
 * Created by Whiyew on 31/03/2017.
 */

public class Erro extends BroadcastReceiver {
    Vibrator vibrator;

    @Override
    public void onReceive(final Context context, Intent intent) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        final DatabaseReference myRef = database.getReference(android_id);
        myRef.child("Warn").child("check").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot.getValue().toString().equals("true") == true) {
                        myRef.child("Warn").child("check").setValue("false", new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    WakeLocker.acquire(context);


                                    //  vibrator.vibrate(10000);
                                    Intent i = new Intent(context, Warning.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                }
                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//        Intent background = new Intent(context, UpdateMarker.class);
//        context.startService(background);

}
