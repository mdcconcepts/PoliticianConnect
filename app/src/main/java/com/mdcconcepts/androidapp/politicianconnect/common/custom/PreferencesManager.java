package com.mdcconcepts.androidapp.politicianconnect.common.custom;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * {@link PreferencesManager} class used to manage shared preferences
 * Created by CODELORD on 1/30/2015.
 *
 * @author CODELORD
 */
public class PreferencesManager {

    public static final String TAG = "PreferencesManager";
    private static final String PREF_NAME = "com.mdcconcepts.androidapp.politicianconnect.PREF_NAME";

    private static class Key {
        /**
         * This variable is used for storing key for Language for overall application. {@code 1=>Hindi,2=>Marathi,3=>English}
         */
        private static final String LANGUAGE_VALUE = "LANGUAGE_VALUE";
        private static final String USER_DATA = "USER_DATA";
        private static final String USER_ID = "USER_ID";
    }


    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setLanguageValue(int value) {
        mPref.edit()
                .putInt(Key.LANGUAGE_VALUE, value)
                .apply();
    }

    public int getLanguageValue() {
        return mPref.getInt(Key.LANGUAGE_VALUE, 0);
    }

    public void removeLanguage() {
        mPref.edit()
                .remove(Key.LANGUAGE_VALUE)
                .apply();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

    public void setUserData(String UserData) {
        mPref.edit()
                .putString(Key.USER_DATA, UserData)
                .apply();
    }

    public String getUserData() {
        return mPref.getString(Key.USER_DATA, "");
    }

    public void setUserId(int UserId) {
        mPref.edit()
                .putInt(Key.USER_ID, UserId)
                .apply();
    }

    public int getUserId() {
        return mPref.getInt(Key.USER_ID, -1);
    }
}
