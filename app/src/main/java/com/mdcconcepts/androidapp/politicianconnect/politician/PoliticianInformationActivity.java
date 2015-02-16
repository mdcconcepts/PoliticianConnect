package com.mdcconcepts.androidapp.politicianconnect.politician;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.util.AppConstants;

public class PoliticianInformationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician_information);

        Intent myIntent = getIntent(); // gets the previously created intent
        int is_aim = myIntent.getIntExtra("is_aim", 0); // will return "FirstKeyValue"


        restoreActionBar();

        WebView WebView_information = (WebView) findViewById(R.id.WebView_information);
        WebView_information.getSettings().setJavaScriptEnabled(true);
        if (is_aim == 0) {
            WebView_information.loadUrl(Post_URL.DOMAIN + "index.php/adminPanel/view?ADMIN_TOKEN=" + AppConstants.ADMIN_ID);
        } else {
            WebView_information.loadUrl(Post_URL.DOMAIN + "index.php/adminPanel/goals?ADMIN_TOKEN=" + AppConstants.ADMIN_ID);
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

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
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

        String Title = Label_Text_array[8];

        actionBar.setTitle(Title);
    }
}
