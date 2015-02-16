package com.mdcconcepts.androidapp.politicianconnect.common;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mdcconcepts.androidapp.politicianconnect.HomeActivity;
import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;

public class SettingActivity extends ActionBarActivity {

    public static final String TAG = "SettingActivity";


    /**
     * XML controller for {@link LanguageActivity}
     */
    private RadioButton ButtonController_Hindi;
    private RadioButton ButtonController_Marathi;
    private RadioButton ButtonController_English;
    private RadioGroup RadioGroup_Language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        restoreActionBar();
        init_params();

//        ButtonController_Hindi.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                setLanguageAndOpenLogin(1);
//            }
//        });
//
//
//        ButtonController_Marathi.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                setLanguageAndOpenLogin(2);
//            }
//        });
//
//
//        ButtonController_English.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                setLanguageAndOpenLogin(3);
//            }
//        });

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

        Title = Label_Text_array[28];

        actionBar.setTitle(Title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to initialise all variables going to used in the Activity {@link LanguageActivity}
     */
    private void init_params() {

        ButtonController_Hindi = (RadioButton) findViewById(R.id.ButtonController_Hindi);
        ButtonController_Marathi = (RadioButton) findViewById(R.id.ButtonController_Marathi);
        ButtonController_English = (RadioButton) findViewById(R.id.ButtonController_English);

        RadioGroup_Language = (RadioGroup) findViewById(R.id.RadioGroup_Language);

        PreferencesManager.initializeInstance(this);

        switch (PreferencesManager.getInstance().getLanguageValue()) {
            case 1:
                ButtonController_Hindi.setChecked(true);
                break;
            case 2:
                ButtonController_Marathi.setChecked(true);
                break;
            case 3:
                ButtonController_English.setChecked(true);
                break;
        }

        RadioGroup_Language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                switch (checkedId) {
                    case R.id.ButtonController_Hindi:
                        if (ButtonController_Hindi.isChecked()) {
                            setLanguageAndOpenLogin(1);
                        }
                        break;
                    case R.id.ButtonController_Marathi:
                        if (ButtonController_Marathi.isChecked()) {
                            setLanguageAndOpenLogin(2);
                        }
                        break;
                    case R.id.ButtonController_English:
                        if (ButtonController_English.isChecked()) {
                            setLanguageAndOpenLogin(3);
                        }
                        break;
                }
            }
        });

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

//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//
//        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        finish();
    }
}
