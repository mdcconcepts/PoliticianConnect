package com.mdcconcepts.androidapp.politicianconnect.common.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ajay on 27/11/14.
 */
public interface CompletionListener {

    public void onComplete(JSONObject serverResponse, int Response_From) throws JSONException;

}
