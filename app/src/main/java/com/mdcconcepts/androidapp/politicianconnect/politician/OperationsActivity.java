package com.mdcconcepts.androidapp.politicianconnect.politician;

import android.content.Intent;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OperationsActivity extends ActionBarActivity implements CompletionListener {

    private static final String TAG = "OperationsActivity";

    /**
     * XML Controllers
     */
    private ProgressBar ProgressBar_Operation;
    private ListView ListView_OperationList;

    private ArrayList<OperationContainer> OperationArrayList = new ArrayList<OperationContainer>();
    private int is_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent myIntent = getIntent(); // gets the previously created intent
        is_project = myIntent.getIntExtra("is_project", 0); // will return "FirstKeyValue"


        restoreActionBar();

        ProgressBar_Operation = (ProgressBar) findViewById(R.id.ProgressBar_Operation);

        ListView_OperationList = (ListView) findViewById(R.id.ListView_OperationList);

        NetworkTask networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETPROJECTDETAILS, 1);


    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
        actionBar.setTitle(setMultiLingualData(PreferencesManager.getInstance().getLanguageValue()));
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

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private String setMultiLingualData(int languageCode) {

        String[] Label_Text_array;
        switch (languageCode) {
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

        if (is_project == 1) {
            return Label_Text_array[13];
        }
        return Label_Text_array[12];
    }

    private List<NameValuePair> getRequestParams() {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("is_project", "" + is_project));

        return params;
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

                JSONArray Service = serverResponse.getJSONArray("Message");

                for (int i = 0; i < Service.length(); i++) {

                    OperationArrayList.add(new OperationContainer(Integer.parseInt(Service.getJSONObject(i).getString("ID")), Service.getJSONObject(i).getString("LOCATION"), Service.getJSONObject(i).getString("DATE"), Service.getJSONObject(i).getString("TITLE"), Service.getJSONObject(i).getString("DESR"), Service.getJSONObject(i).getString("LOGO")));
                }

                ListView_OperationList.setAdapter(new Operation_Custom_Adapter(this, OperationArrayList));


                Util.log(TAG, "Operations Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_Operation.setVisibility(View.GONE);
    }
}
