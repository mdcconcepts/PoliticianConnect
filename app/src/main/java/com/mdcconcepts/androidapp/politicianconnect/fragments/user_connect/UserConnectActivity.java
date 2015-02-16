package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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


public class UserConnectActivity extends ActionBarActivity implements CompletionListener {
    //TODO Documentation Here
    private static final String TAG = "UserConnectActivity";

    private ArrayList<MessageContainer> mMessageContainerList = new ArrayList<MessageContainer>();

    NetworkTask networkTask;

    /**
     * XML Controllers
     */
    private ListView mMessageList;
    private Button Button_Send;
    private EditText EditText_ContactSubject;
    private EditText EditText_ContactMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_connect);
        restoreActionBar();
        init_params();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
        String Title = "";
        String[] Label_Text_array;
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

        Title = Label_Text_array[14];

        actionBar.setTitle(Title);
    }

    private void init_params() {

        mMessageList = (ListView) findViewById(R.id.ListView_MessageList);

        Button_Send = (Button) findViewById(R.id.Button_Send);

        EditText_ContactSubject = (EditText) findViewById(R.id.EditText_ContactSubject);

        EditText_ContactMessage = (EditText) findViewById(R.id.EditText_ContactMessage);

        networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams(1);

        networkTask.execute(params, Post_URL.URL_GETCONVERSATION, 1);

        mMessageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(UserConnectActivity.this, ChatRoomActivity.class);
                myIntent.putExtra("question_id", mMessageContainerList.get(position).getId());
                startActivity(myIntent);

            }
        });

        Button_Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (EditText_ContactSubject.getText().toString().trim().length() == 0) {

                    String Message;

                    switch (PreferencesManager.getInstance().getLanguageValue()) {
                        case 2:
                            Message = "कृपया विषय भरा  ";
                            break;
                        case 1:
                            Message = "कृपया विषय दर्ज करें";
                            break;
                        default:
                            Message = "Please Enter Subject correct !";
                            break;
                    }

                    EditText_ContactSubject.setError(Message);

                } else if (EditText_ContactMessage.getText().toString().trim().length() == 0) {
                    String Message;

                    switch (PreferencesManager.getInstance().getLanguageValue()) {
                        case 2:
                            Message = "कृपया संदेश भरा  ";
                            break;
                        case 1:
                            Message = "कृपया संदेश दर्ज करें";
                            break;
                        default:
                            Message = "Please Enter Message correct !";
                            break;
                    }

                    EditText_ContactMessage.setError(Message);
                } else {
                    List<NameValuePair> params = getRequestParams(2);

                    networkTask = new NetworkTask(UserConnectActivity.this, UserConnectActivity.this, false);

                    networkTask.execute(params, Post_URL.URL_SUBMITCONVERSATION, 2);

                }
            }

        });

    }


    private List<NameValuePair> getRequestParams(int requests) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        switch (requests) {
            case 1:
                params.add(new BasicNameValuePair("user_id", "" + PreferencesManager.getInstance().getUserId()));

                break;
            case 2:
                params.add(new BasicNameValuePair("user_id", "" + PreferencesManager.getInstance().getUserId()));
                params.add(new BasicNameValuePair("title", EditText_ContactSubject.getText().toString().trim()));
                params.add(new BasicNameValuePair("message", EditText_ContactMessage.getText().toString().trim()));

                break;
        }

        return params;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_connect, menu);
        return true;
    }

    @Override
    public void onComplete(JSONObject serverResponse, int RESPONSE_IDENTIFIER_FLAG) throws JSONException {

        Util.log(TAG, "RESPONSE_IDENTIFIER_FLAG " + RESPONSE_IDENTIFIER_FLAG);
        switch (RESPONSE_IDENTIFIER_FLAG) {
            case 1:
                handleResponseGetConversation(serverResponse);
                break;
            case 2:
                handleResponseSubmitConversation(serverResponse);
            default:

        }

    }

    /**
     * This method is used to handle Response return from web service.
     *
     * @param serverResponse This is Response return from web service
     */
    private void handleResponseGetConversation(JSONObject serverResponse) {
        int success;
        try {
            String MLA_Photo = serverResponse.getString("MLA_Photo");
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                JSONArray Slider = serverResponse.getJSONArray("Message");

                Util.log(TAG, MLA_Photo);

                mMessageContainerList.clear();

                for (int i = 0; i < Slider.length(); i++) {
                    mMessageContainerList.add(new MessageContainer(Integer.parseInt(Slider.getJSONObject(i).getString("ID")), Slider.getJSONObject(i).getString("Title"), Slider.getJSONObject(i).getString("DATE")));
                }

                mMessageList.setAdapter(new Message_Custom_Adapter(this, mMessageContainerList, MLA_Photo));

                Util.log(TAG, "Gallery Images Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
    }

    /**
     * This method is used to handle Response return from web service.
     *
     * @param serverResponse This is Response return from web service
     */
    private void handleResponseSubmitConversation(JSONObject serverResponse) {
        int success;
        try {
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                EditText_ContactMessage.setText("");

                EditText_ContactSubject.setText("");

                networkTask = new NetworkTask(UserConnectActivity.this, UserConnectActivity.this, false);

                List<NameValuePair> params = getRequestParams(1);

                networkTask.execute(params, Post_URL.URL_GETCONVERSATION, 1);

            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
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
}
