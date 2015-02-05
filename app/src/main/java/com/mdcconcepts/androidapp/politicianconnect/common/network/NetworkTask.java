package com.mdcconcepts.androidapp.politicianconnect.common.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NetworkTask extends AsyncTask<Object, Integer, JSONObject> {

    private static final String TAG = "NetworkTask";
    private CompletionListener parentActivity;
    private Context context;

    private int RESPONSE_IDENTIFIER_FLAG;
    private Boolean dialogFlag;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();


    public NetworkTask(CompletionListener parent, Context context,
                       boolean dialogFlag) {
        parentActivity = parent;
        this.context = context;
        this.dialogFlag = dialogFlag;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (dialogFlag) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Connecting Server ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

    @Override
    protected JSONObject doInBackground(Object... params) {


        List<NameValuePair> params1 = (List<NameValuePair>) params[0];

        String POST_URL = (String) params[1];

        RESPONSE_IDENTIFIER_FLAG = (Integer) params[2];

        // Posting user data to script
        JSONObject json = jsonParser.makeHttpRequest(POST_URL,
                "POST", params1);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            if (dialogFlag) {
                pDialog.dismiss();
            }
            if (jsonObject != null) {

                Log.e(TAG, "Response:" + jsonObject.toString());

                parentActivity.onComplete(jsonObject, 1);

            } else {
                Toast.makeText(context,
                        "Failed! No response received from server..!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
