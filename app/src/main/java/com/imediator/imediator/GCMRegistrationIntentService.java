package com.imediator.imediator;

import android.content.Intent;
import android.app.IntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by gheorgheionut on 8/8/16.
 */
public class GCMRegistrationIntentService extends IntentService {

    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService(){
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM(){
        Intent registrationComplete = null;
        String token = null;
        try{
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token", token);
            Log.v("GCMRegIntentService", "token: "+token);
        }catch(Exception e){
            Log.v("GCMRegIntentService", "Registration Error");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }

        //Send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

}
