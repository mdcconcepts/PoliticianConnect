package com.mdcconcepts.androidapp.politicianconnect.politician;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends ActionBarActivity implements CompletionListener {
    //TODO Documentation Here
    private static final String TAG = "MessageActivity";

    /**
     * XML Controllers
     */
    private ProgressBar ProgressBar_Operation;
    private ListView ListView_OperationList;

    private ArrayList<GCMMessageContainer> gcmMessageContainersArrayList = new ArrayList<GCMMessageContainer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        restoreActionBar();

        ProgressBar_Operation = (ProgressBar) findViewById(R.id.ProgressBar_Operation);

        ListView_OperationList = (ListView) findViewById(R.id.ListView_MessageContainer);

        NetworkTask networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETGCMMESSAGE, 1);

    }

    private List<NameValuePair> getRequestParams() {
        // TODO Enter Comments Here ...

        return new ArrayList<NameValuePair>();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
        String Title = "";
        String[] Label_Text_array;
        PreferencesManager.initializeInstance(this);
        switch (PreferencesManager.getInstance().getLanguageValue()) {
            case 2:
                Label_Text_array = getResources().getStringArray(R.array.Marathi_Strings);
                break;
            case 1:
                Label_Text_array = getResources().getStringArray(R.array.Hindi_Strings);
                break;
            default:
                Label_Text_array = getResources().getStringArray(R.array.English_Strings);
                break;
        }

        Title = Label_Text_array[27];

        actionBar.setTitle(Title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(JSONObject serverResponse, int RESPONSE_IDENTIFIER_FLAG) throws JSONException {

        switch (RESPONSE_IDENTIFIER_FLAG) {
            case 1:
                handleResponse(serverResponse);
                break;
            default:

        }

    }

    /**
     * This method is used to handle Response return from web service.
     *
     * @param serverResponse This is Response return from web service
     */
    private void handleResponse(JSONObject serverResponse) {
        int success;
        try {
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                JSONArray Service = serverResponse.getJSONArray("GCM_Messages");

                for (int i = 0; i < Service.length(); i++) {
                    gcmMessageContainersArrayList.add(new GCMMessageContainer(Integer.parseInt(Service.getJSONObject(i).getString("ID")), Service.getJSONObject(i).getString("MEDIA_TYPE"), Service.getJSONObject(i).getString("URL"), Service.getJSONObject(i).getString("MESSAGE"), Service.getJSONObject(i).getString("TITLE"), Service.getJSONObject(i).getString("DATE")));
                }

                ListView_OperationList.setAdapter(new GCM_Message_Custom_Adapter(this, gcmMessageContainersArrayList));


                Util.log(TAG, "GCM Message Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_Operation.setVisibility(View.GONE);
    }
}
