package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoomActivity extends ActionBarActivity implements CompletionListener {

    public static final String TAG = "ChatRoomActivity";

    /**
     * XML controller for {@link ChatRoomActivity}
     */
    private ImageView ImageView_Photo;
    private ProgressBar ProgressBar_ImageLoader;
    private ListView ListView_ChatThread;
    private Button Button_Send;

    //TextView
    private TextView TextView_Date;
    private TextView EditText_ContactSubject;
    private TextView EditText_ContactMessage;
    private EditText EditText_Reply;

    private NetworkTask mNetworkTask;
    private ArrayList<ReplyContainer> mReplyContainersList = new ArrayList<ReplyContainer>();

    private int mquestion_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent myIntent = getIntent();

        mquestion_id = myIntent.getIntExtra("question_id", 0);

        Util.log(TAG, "data" + mquestion_id);

        restoreActionBar("Chat Room");

        init_params("Chat Room");
    }

    private void init_params(String title) {

        ListView_ChatThread = (ListView) findViewById(R.id.ListView_ChatThread);

        ImageView_Photo = (ImageView) findViewById(R.id.ImageView_Photo);
        ProgressBar_ImageLoader = (ProgressBar) findViewById(R.id.ProgressBar_ImageLoader);

        Button_Send = (Button) findViewById(R.id.Button_Send);

        TextView_Date = (TextView) findViewById(R.id.TextView_Date);
        EditText_ContactSubject = (TextView) findViewById(R.id.EditText_ContactSubject);
        EditText_ContactMessage = (TextView) findViewById(R.id.EditText_ContactMessage);

        EditText_Reply = (EditText) findViewById(R.id.EditText_Reply);

        mNetworkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams(mquestion_id, 0);

        mNetworkTask.execute(params, Post_URL.URL_GETCONVERSATIONDETAILS, 1);

        mNetworkTask = new NetworkTask(this, this, false);

        mNetworkTask.execute(params, Post_URL.URL_GETCONVERSATIONREPLY, 2);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        Button_Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (EditText_Reply.getText().toString().trim().length() == 0) {
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
                    List<NameValuePair> params = getRequestParams(mquestion_id, 1);

                    mNetworkTask = new NetworkTask(ChatRoomActivity.this, ChatRoomActivity.this, false);

                    mNetworkTask.execute(params, Post_URL.URL_SUBMITREPLY, 3);

                }
            }

        });

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


    private List<NameValuePair> getRequestParams(int question_id, int select) {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        if (select == 0) {
            params.add(new BasicNameValuePair("question_id", "" + question_id));
        } else {
            params.add(new BasicNameValuePair("question_id", "" + question_id));
            params.add(new BasicNameValuePair("user_id", "4"));
            params.add(new BasicNameValuePair("message", EditText_Reply.getText().toString().trim()));
        }
        return params;
    }

    public void restoreActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
//        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_menu));
        actionBar.setTitle(title);
    }

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     * @return This returns String List of Menu titles
     */
    private String[] setMultiLingualData(int languageCode) {

        switch (languageCode) {
            case 2:
                return getResources().getStringArray(R.array.nav_drawer_items);
            case 1:
                return getResources().getStringArray(R.array.hn_nav_drawer_items);
            default:
                return getResources().getStringArray(R.array.en_nav_drawer_items);
        }
    }

    @Override
    public void onComplete(JSONObject serverResponse, int RESPONSE_IDENTIFIER_FLAG) throws JSONException {

        switch (RESPONSE_IDENTIFIER_FLAG) {
            case 1:
                handleResponseSingleQuestion(serverResponse);
                break;
            case 2:
                handleResponseQuestionReply(serverResponse);
                break;
            case 3:
                handleResponseSubmitConversation(serverResponse);
                break;
            default:

        }

    }

    /**
     * This method is used to handle Response return from web service.
     *
     * @param serverResponse This is Response return from web service
     */
    private void handleResponseSingleQuestion(JSONObject serverResponse) {
        int success;
        try {
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                JSONObject Message = serverResponse.getJSONObject("Message");

                String MLA_Photo = serverResponse.getString("MLA_Photo");

                String url;

                url = Post_URL.DOMAIN + MLA_Photo;


                Picasso.with(this)
                        .load(url)
                        .into(ImageView_Photo, new Callback() {

                            @Override
                            public void onSuccess() {
                                ImageView_Photo.setVisibility(View.VISIBLE);
                                ProgressBar_ImageLoader.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError() {
                                ProgressBar_ImageLoader.setVisibility(View.VISIBLE);
                                ImageView_Photo.setVisibility(View.INVISIBLE);
                            }
                        });


                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(Message.getString("DATE"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView_Date.setText(new SimpleDateFormat("dd MMM yyyy").format(date));

                EditText_ContactSubject.setText(Message.getString("Title"));

                EditText_ContactMessage.setText(Message.getString("QUESTION"));


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_ImageLoader.setVisibility(View.GONE);
    }

    /**
     * This method is used to handle Response return from web service.
     *
     * @param serverResponse This is Response return from web service
     */
    private void handleResponseQuestionReply(JSONObject serverResponse) {
        int success;
        try {
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {
                JSONArray Reply_Message = serverResponse.getJSONArray("Reply_Message");

                mReplyContainersList.clear();

                for (int i = 0; i < Reply_Message.length(); i++) {

                    mReplyContainersList.add(new ReplyContainer(Reply_Message.getJSONObject(i).getString("REPLY"), Reply_Message.getJSONObject(i).getString("DATE"), Integer.parseInt(Reply_Message.getJSONObject(i).getString("REPLY_BY"))));
                }

                ListView_ChatThread.setAdapter(new Message_Reply_Custom_Adapter(ChatRoomActivity.this, mReplyContainersList));
                ListView_ChatThread.setSelection(ListView_ChatThread.getCount() - 1);

            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_ImageLoader.setVisibility(View.GONE);
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

                EditText_Reply.setText("");

                mNetworkTask = new NetworkTask(ChatRoomActivity.this, ChatRoomActivity.this, false);

                List<NameValuePair> params = getRequestParams(mquestion_id, 0);

                mNetworkTask.execute(params, Post_URL.URL_GETCONVERSATIONREPLY, 2);

            } else {

                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
    }

}