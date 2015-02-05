package com.mdcconcepts.androidapp.politicianconnect.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mdcconcepts.androidapp.politicianconnect.HomeActivity;
import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LanguageActivity extends Activity {

    public static final String TAG = "LanguageActivity";

    /**
     * XML controller for {@link LanguageActivity}
     */
    private Button ButtonController_Hindi;
    private Button ButtonController_Marathi;
    private Button ButtonController_English;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        init_params();

        String USER_DATA = PreferencesManager.getInstance().getUserData();

        // If User Data Present redirect to Home Activity
        if (USER_DATA.length() != 0) {

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        ButtonController_Hindi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLanguageAndOpenLogin(1);
            }
        });


        ButtonController_Marathi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLanguageAndOpenLogin(2);
            }
        });


        ButtonController_English.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setLanguageAndOpenLogin(3);
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    /**
     * This method is used to initialise all variables going to used in the Activity {@link LanguageActivity}
     */
    private void init_params() {

        ButtonController_Hindi = (Button) findViewById(R.id.ButtonController_Hindi);
        ButtonController_Marathi = (Button) findViewById(R.id.ButtonController_Marathi);
        ButtonController_English = (Button) findViewById(R.id.ButtonController_English);

        PreferencesManager.initializeInstance(this);

        /**
         * Check whether Language Already Selected or not
         */
//        if (PreferencesManager.getInstance().getLanguageValue() == 0) {
//            setLanguageAndOpenLogin(0);
//        }
    }

    /**
     * This method is used to set Language Value and open another intent.
     *
     * @param language This variable is used to select language for application.
     */
    private void setLanguageAndOpenLogin(int language) {
        PreferencesManager.getInstance().setLanguageValue(language);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }
}
