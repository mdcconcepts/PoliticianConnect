package com.mdcconcepts.androidapp.politicianconnect.common.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>{@link Util}</h1>
 * <p>This class is used for storing constants and variables </p>
 * Created by CODELORD on 1/29/2015.
 *
 * @author CODELORD
 */
public class Util {

    private static final boolean is_under_development = false;
    private static final String TAG = "Util";

    /**
     * <h1>log</h1>
     * This function is used for printing data on console.
     *
     * @param TAG     TAG for Message.
     * @param Message Message going to display on console.
     */
    public static void log(String TAG, String Message) {
        Log.e(TAG, Message);
    }

    /**
     * <h1>setFont</h1>
     * <p>This Method is public method of {@link Util}, using this function we can change
     * fonts of all controller which we are going to used in application.</p>
     *
     * @param controller This is XML controller of which we are going to change text font.
     * @param type       This defines controller type. {@code 1=> Button}
     * @param lang_cat   This define of which language we are going to convert the text. {@code 1=>Marathi}
     */
    public static void setFont(Object controller, int type, int lang_cat, Context context) {

        Typeface font = null;
        switch (lang_cat) {
            case 1:
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Shivaji01.ttf");
                break;
        }

        switch (type) {
            case 1:

                Button button = (Button) controller;
                button.setTypeface(font);
                break;
        }

    }

    /**
     * <H>getGeoLocationJson</H>
     * <p>This Static Method of {@link com.mdcconcepts.androidapp.politicianconnect.common.util.Util}</p>
     * <p>which used to get Data of State and City from Json file stored in assets folder.</p>
     *
     * @param context      This context of current activity in which we are going to use {@link #getGeoLocationJson(android.content.Context, int)} method.
     * @param languageCode This variable define whether we have to open Marathi or Hindi Or English.
     * @return This will return {@link org.json.JSONObject} of State and City.
     * @throws JSONException
     */
    public static JSONObject getGeoLocationJson(Context context, int languageCode) throws JSONException {
        String json = null;
        InputStream is;
        try {

            switch (languageCode) {
                case 1:
                    is = context.getAssets().open("json_data/mh_geo_location_state_city_pincode.json");
                    break;
                case 2:
                    is = context.getAssets().open("json_data/mh_geo_location_state_city_pincode.json");
                    break;
                default:
                    is = context.getAssets().open("json_data/geo_location_state_city_pincode.json");
                    break;
            }


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return new JSONObject(json);
    }
}
