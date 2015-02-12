package com.mdcconcepts.androidapp.politicianconnect.fragments.services.subservice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.mdcconcepts.androidapp.politicianconnect.common.util.AppConstants;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubServiceActivity extends ActionBarActivity implements CompletionListener {
    public static final String TAG = "SubServiceActivity";


    /**
     * XML controller for {@link SubServiceActivity}
     */
    private ImageView ImageView_Photo;
    private ProgressBar ProgressBar_ImageLoader;
    private Button Button_action;
    private ProgressBar ProgressBar_Webview;
    private WebView WebView_Info;

    //TextView
    private TextView TextView_ShopName;
    private TextView TextView_ShopAddress;
    private TextView TextView_ShopMobile;
    private TextView TextView_ShopLandline;
    private TextView TextView_Mobile;
    private TextView TextView_Landline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_service);

        Intent myIntent = getIntent(); // gets the previously created intent
        int shoppingid = myIntent.getIntExtra("productid", 0); // will return "FirstKeyValue"
        String title = myIntent.getStringExtra("prodcut_title"); // will return "Second

        Util.log(TAG, "Product id" + shoppingid);
        restoreActionBar(title);
        init_params(shoppingid, title);
    }

    private void init_params(int shoppingid, String title) {

        ImageView_Photo = (ImageView) findViewById(R.id.ImageView_Photo);
        ProgressBar_ImageLoader = (ProgressBar) findViewById(R.id.ProgressBar_ImageLoader);
        Button_action = (Button) findViewById(R.id.Button_action);
        ProgressBar_Webview = (ProgressBar) findViewById(R.id.ProgressBar_Webview);
        WebView_Info = (WebView) findViewById(R.id.WebView_Info);

        TextView_ShopName = (TextView) findViewById(R.id.TextView_ShopName);
        TextView_ShopAddress = (TextView) findViewById(R.id.TextView_ShopAddress);
        TextView_ShopMobile = (TextView) findViewById(R.id.TextView_ShopMobile);
        TextView_ShopLandline = (TextView) findViewById(R.id.TextView_ShopLandline);
        TextView_Mobile = (TextView) findViewById(R.id.TextView_Mobile);
        TextView_Landline = (TextView) findViewById(R.id.TextView_Landline);

        NetworkTask networkTask = new NetworkTask(this, this, false);

        List<NameValuePair> params = getRequestParams(shoppingid);

        networkTask.execute(params, Post_URL.URL_GETPRODUCTDETAILS, 1);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        WebView_Info.getSettings().setJavaScriptEnabled(true);
        WebView_Info.loadUrl(Post_URL.DOMAIN + "politician/index.php/adminPanel/servicesView?ADMIN_TOKEN=" + AppConstants.ADMIN_ID + "&SERVICE_ID=" + shoppingid);

        WebView_Info.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressBar_Webview.setVisibility(View.GONE);
            }


            @Override
            public void onReceivedError(WebView view,
                                        int errCode, String description, String failedUrl) {
                String html1 = "<html><body><br><br><div><h3>An error occurred.</h3><hr>";
                String html2 = "<p>Check your Internet connection!</p></div></body></html>";
                String mime = "text/html";
                String encoding = "utf-8";
                view.loadData(html1 + html2, mime, encoding);
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


    private List<NameValuePair> getRequestParams(int shoppingid) {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("productid", "" + shoppingid));

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

                JSONArray ShoppingProduct = serverResponse.getJSONArray("ShoppingProduct");

                for (int i = 0; i < ShoppingProduct.length(); i++) {


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

                    Button_action.setText(Label_Text_array[19]);


                    String url;

                    url = Post_URL.DOMAIN + ShoppingProduct.getJSONObject(i).getString("LOGO");


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

                    TextView_ShopName.setText(ShoppingProduct.getJSONObject(i).getString("TITLE"));

                    TextView_ShopAddress.setText(ShoppingProduct.getJSONObject(i).getString("ADDRESS"));

                    TextView_ShopMobile.setText(ShoppingProduct.getJSONObject(i).getString("MOBILE"));

                    TextView_ShopLandline.setText(ShoppingProduct.getJSONObject(i).getString("LANDLINE"));

                    TextView_Mobile.setText(Label_Text_array[21]);

                    TextView_Landline.setText(Label_Text_array[22]);

                    Button_action.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String contact_no = TextView_ShopMobile.getText().toString();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + contact_no));
                            startActivity(callIntent);

                        }
                    });
//                    21 22
                }


                Util.log(TAG, "Sub Service Data Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_ImageLoader.setVisibility(View.GONE);
    }
}
