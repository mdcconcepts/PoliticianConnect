package com.mdcconcepts.androidapp.politicianconnect.politician;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect.MessageContainer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MLAContactActivity extends ActionBarActivity implements CompletionListener {
    //TODO Documentation Here
    private static final String TAG = "UserConnectActivity";

    private ArrayList<MessageContainer> mMessageContainerList = new ArrayList<MessageContainer>();

    NetworkTask networkTask;

    /**
     * XML Controllers
     */
    private ImageView mImageView_Photo;
    private ProgressBar mProgressBar_ImageLoader;

    private TextView mTextView_MLA_Name;
    private TextView TextView_Mobile;
    private TextView TextView_Email;
    private TextView TextView_OfficePhone;
    private TextView TextView_Office_Address;
    private TextView TextView_lblMobile;
    private TextView TextView_lblEmail;
    private TextView TextView_lblOfficePhone;
    private TextView TextView_lblOffice_Address;

    private Button Button_Call;
    private Button Button_Email;
    private Button Button_OfficeCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlacontact);
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

        Title = Label_Text_array[15];

        actionBar.setTitle(Title);
    }

    private void init_params() {

        mImageView_Photo = (ImageView) findViewById(R.id.ImageView_Photo);

        mProgressBar_ImageLoader = (ProgressBar) findViewById(R.id.ProgressBar_ImageLoader);

        mTextView_MLA_Name = (TextView) findViewById(R.id.TextView_MLA_Name);

        TextView_Mobile = (TextView) findViewById(R.id.TextView_Mobile);
        TextView_Email = (TextView) findViewById(R.id.TextView_Email);
        TextView_OfficePhone = (TextView) findViewById(R.id.TextView_OfficePhone);
        TextView_Office_Address = (TextView) findViewById(R.id.TextView_Office_Address);

        TextView_lblMobile = (TextView) findViewById(R.id.TextView_lblMobile);
        TextView_lblEmail = (TextView) findViewById(R.id.TextView_lblEmail);
        TextView_lblOfficePhone = (TextView) findViewById(R.id.TextView_lblOfficePhone);
        TextView_lblOffice_Address = (TextView) findViewById(R.id.TextView_lblOffice_Address);

        Button_Call = (Button) findViewById(R.id.Button_Call);
        Button_Email = (Button) findViewById(R.id.Button_Email);
        Button_OfficeCall = (Button) findViewById(R.id.Button_OfficeCall);

        Button_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact_no = TextView_Mobile.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact_no));
                startActivity(callIntent);
            }
        });
        Button_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse(TextView_Email.getText().toString().trim()));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                startActivity(sendIntent);
            }
        });
        Button_OfficeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact_no = TextView_OfficePhone.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact_no));
                startActivity(callIntent);
            }
        });

        networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETMLACONTACT, 1);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());
    }

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private void setMultiLingualData(int languageCode) {

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

        TextView_lblMobile.setText(Label_Text_array[21]);
        TextView_lblEmail.setText(Label_Text_array[24]);
        TextView_lblOfficePhone.setText(Label_Text_array[25]);
        TextView_lblOffice_Address.setText(Label_Text_array[26]);
        Button_Call.setText(Label_Text_array[19]);
        Button_Email.setText(Label_Text_array[24]);
        Button_OfficeCall.setText(Label_Text_array[19]);


    }

    private List<NameValuePair> getRequestParams() {
        return new ArrayList<NameValuePair>();
    }

    @Override
    public void onComplete(JSONObject serverResponse, int RESPONSE_IDENTIFIER_FLAG) throws JSONException {

        Util.log(TAG, "RESPONSE_IDENTIFIER_FLAG " + RESPONSE_IDENTIFIER_FLAG);
        switch (RESPONSE_IDENTIFIER_FLAG) {
            case 1:
                handleResponseGetConversation(serverResponse);
                break;
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
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                JSONObject MLA_info = serverResponse.getJSONObject("MLA_Contact");
                String url = Post_URL.DOMAIN + MLA_info.getString("PHOTO");
                Picasso.with(MLAContactActivity.this)
                        .load(url)
                        .into(mImageView_Photo, new Callback() {

                            @Override
                            public void onSuccess() {
                                mImageView_Photo.setVisibility(View.VISIBLE);
                                mProgressBar_ImageLoader.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError() {
                                mProgressBar_ImageLoader.setVisibility(View.VISIBLE);
                                mImageView_Photo.setVisibility(View.INVISIBLE);
                            }
                        });


                mTextView_MLA_Name.setText(MLA_info.getString("NAME"));
                TextView_Mobile.setText(MLA_info.getString("CONTACT"));
                TextView_Email.setText(MLA_info.getString("EMAIL"));
                TextView_OfficePhone.setText(MLA_info.getString("OFFICE_CONTACT"));
                TextView_Office_Address.setText(MLA_info.getString("OFFICE_ADDRESS"));
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
