package com.mdcconcepts.androidapp.politicianconnect.gallary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class GalleryActivity extends ActionBarActivity implements CompletionListener {

    private static final String TAG = "GalleryActivity";

    private ArrayList<String> Gallery_Images = new ArrayList<String>();

    private int media;

    /**
     * XML Controllers
     */
    private GridView gridView;
    private ProgressBar ProgressBar_GalleryLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();

        media = b.getInt("media");

        setContentView(R.layout.activity_gallery);

        restoreActionBar();

        init_params();
    }

    private void init_params() {
        gridView = (GridView) findViewById(R.id.gridview_gallary);

        ProgressBar_GalleryLoader = (ProgressBar) findViewById(R.id.ProgressBar_GalleryLoader);

        NetworkTask networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams(media);

        networkTask.execute(params, Post_URL.URL_GETGALLERY, 1);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (media) {
                    case 0:
                        Intent intent = new Intent(GalleryActivity.this, GalleryViewActivity.class);
                        Bundle b = new Bundle();
                        b.putString("Image_URL", Gallery_Images.get(position));
                        intent.putExtras(b);
                        startActivity(intent);

                        break;
                    default:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + Gallery_Images.get(position)));
                        startActivity(intent);
                        break;
                }

            }
        });
    }

    private List<NameValuePair> getRequestParams(int media) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("media", "" + media));

        return params;
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
        switch (media) {
            case 0:
                Title = Label_Text_array[10];
                break;
            default:
                Title = Label_Text_array[11];
                break;
        }
        actionBar.setTitle(Title);
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

                JSONArray Slider = serverResponse.getJSONArray("Gallery");

                for (int i = 0; i < Slider.length(); i++) {
                    Gallery_Images.add(Slider.getJSONObject(i).getString("URL"));
                }

                gridView.setAdapter(new Image_Custom_Adapter(this, Gallery_Images, media));

                Util.log(TAG, "Gallery Images Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_GalleryLoader.setVisibility(View.GONE);
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
