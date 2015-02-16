package com.mdcconcepts.androidapp.politicianconnect;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.mdcconcepts.androidapp.politicianconnect.common.LoginActivity;
import com.mdcconcepts.androidapp.politicianconnect.common.gcm.ServerUtilities;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.mdcconcepts.androidapp.politicianconnect.politician.MessageActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mdcconcepts.androidapp.politicianconnect.common.gcm.CommonUtilities.SENDER_ID;
import static com.mdcconcepts.androidapp.politicianconnect.common.gcm.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService implements CompletionListener {

    private static final String TAG = "GCMIntentService";

    /**
     * Network Variable
     */
    private NetworkTask networkTask;

    public GCMIntentService() {
        super(SENDER_ID);
    }


    /**
     * Method called on device registered
     */
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");

        networkTask = new NetworkTask(GCMIntentService.this, context, false);

        /**
         * Network Work
         */
        List<NameValuePair> params = getRequestParams(registrationId);

        networkTask.execute(params, Post_URL.URL_REGISTRATION, 1);

//        ServerUtilities.register(context, "vikram ghadge", "vikramghadge@gmail.com", registrationId);

    }

    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    private List<NameValuePair> getRequestParams(String registrationId) {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("name", LoginActivity.EditText_LoginName.getText().toString().trim()));
        params.add(new BasicNameValuePair("gender", "" + LoginActivity.Spinner_Gender.getSelectedItemPosition()));
        params.add(new BasicNameValuePair("dob", LoginActivity.BirthDate));
        params.add(new BasicNameValuePair("mobile", LoginActivity.EditText_LoginMobileNo.getText().toString().trim()));
        params.add(new BasicNameValuePair("state", "" + LoginActivity.Spinner_State.getSelectedItemPosition()));
        params.add(new BasicNameValuePair("city", "" + LoginActivity.Spinner_City.getSelectedItemPosition()));
        params.add(new BasicNameValuePair("gcm_id", registrationId));


        return params;
    }

    /**
     * Method called on Receiving a new message
     */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");

        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, MessageActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }

    @Override
    public void onComplete(JSONObject serverResponse, int RESPONSE_IDENTIFIER_FLAG) throws JSONException {

        switch (RESPONSE_IDENTIFIER_FLAG) {
            case 1:

                Util.log(TAG, "User Register With GCM");

                break;
            default:

        }

    }
}
