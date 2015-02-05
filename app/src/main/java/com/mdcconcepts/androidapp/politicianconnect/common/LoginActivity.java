package com.mdcconcepts.androidapp.politicianconnect.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mdcconcepts.androidapp.politicianconnect.HomeActivity;
import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.Validator;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.ConnectionDetector;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.user.User;
import com.mdcconcepts.androidapp.politicianconnect.common.util.AppConstants;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LoginActivity extends Activity implements CompletionListener {


    public static final String TAG = "LoginActivity";

    private ArrayList<String> StateArrayList = new ArrayList<String>();
    private ArrayList<String> CityArrayList = new ArrayList<String>();
    private JSONArray StateJsonArray = null;
    private ConnectionDetector connectionDetector;
    private boolean isDateSelected = false;
    private String BirthDate = "";

    /**
     * XML controller for {@link LoginActivity}
     */
    //TextView
    private TextView TextView_LabelLoginName;
    private TextView TextView_LabelLoginMobile;
    private TextView TextView_LabelState;
    private TextView TextView_LabelCity;
    private TextView TextView_LabelGender;
    private TextView TextView_LabelBirthDate;
    private TextView TextView_LoginHeading;

    //EditText
    private EditText EditText_LoginName;
    private EditText EditText_LoginMobileNo;
    private Spinner Spinner_State;
    private Spinner Spinner_City;
    private Spinner Spinner_Gender;
    private TextView EditText_LoginBirthDate;
    private Button Button_Next;


    /**
     * Network Variable
     */
    private NetworkTask networkTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            init_params();
        } catch (Exception ex) {
            Util.log(TAG, ex.getMessage());
        }
    }


    /**
     * This method is used to initialise all variables going to used in the Activity {@link LoginActivity}
     *
     * @throws JSONException
     */
    private void init_params() throws JSONException {

        /**
         * Multi Lingual Label Init
         */
        TextView_LabelLoginName = (TextView) findViewById(R.id.TextView_LabelLoginName);
        TextView_LabelLoginMobile = (TextView) findViewById(R.id.TextView_LabelLoginMobile);
        TextView_LabelState = (TextView) findViewById(R.id.TextView_LabelState);
        TextView_LabelCity = (TextView) findViewById(R.id.TextView_LabelCity);
        TextView_LabelGender = (TextView) findViewById(R.id.TextView_LabelGender);
        TextView_LabelBirthDate = (TextView) findViewById(R.id.TextView_LabelBirthDate);
        TextView_LoginHeading = (TextView) findViewById(R.id.TextView_LoginHeading);

        /**
         * Controller Init
         */
        EditText_LoginName = (EditText) findViewById(R.id.EditText_LoginName);
        EditText_LoginMobileNo = (EditText) findViewById(R.id.EditText_LoginMobileNo);
        Spinner_State = (Spinner) findViewById(R.id.Spinner_State);
        Spinner_City = (Spinner) findViewById(R.id.Spinner_City);
        Spinner_Gender = (Spinner) findViewById(R.id.Spinner_Gender);
        EditText_LoginBirthDate = (TextView) findViewById(R.id.EditText_LoginBirthDate);
        Button_Next = (Button) findViewById(R.id.Button_Next);

        Spinner_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

                try {

                    JSONObject State = StateJsonArray.getJSONObject(position);

                    JSONArray CityJsonArray = State.getJSONArray("Cities");

                    for (int i = 0; i < CityJsonArray.length(); i++) {

                        JSONObject City = CityJsonArray.getJSONObject(i);
                        CityArrayList.add(City.getString("name"));

                    }


                    ArrayAdapter adapter = new ArrayAdapter(LoginActivity.this,
                            R.layout.spinner_item, CityArrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner_City.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });

        Button_Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isInternetPresent = connectionDetector
                        .isConnectingToInternet();

                if (isInternetPresent) {

                    if (!Validator.isValidName(EditText_LoginName.getText().toString().trim())) {

                        String Message;

                        switch (PreferencesManager.getInstance().getLanguageValue()) {
                            case 2:
                                Message = "कृपया तुमचे पूर्ण नाव भरा  ";
                                break;
                            case 1:
                                Message = "कृपया अपना पूरा नाम दर्ज करें";
                                break;
                            default:
                                Message = "Please Enter Your Full Name correct !";
                                break;
                        }

                        EditText_LoginName.setError(Message);
                    } else if (!Validator.isValidPhoneNumber(EditText_LoginMobileNo.getText().toString().trim())) {
                        String Message;

                        switch (PreferencesManager.getInstance().getLanguageValue()) {
                            case 2:
                                Message = "कृपया तुमचे फोन नंबर भरा  ";
                                break;
                            case 1:
                                Message = "कृपया अपना फोन नंबर दर्ज करें";
                                break;
                            default:
                                Message = "Please Enter Your Phone Number correct !";
                                break;
                        }

                        EditText_LoginMobileNo.setError(Message);
                    } else if (!isDateSelected) {

                        String Header, Message;

                        switch (PreferencesManager.getInstance().getLanguageValue()) {
                            case 2:
                                Header = "जन्म तारीख ";
                                Message = "आपली जन्म तारीख प्रविष्ट करा !";
                                break;
                            case 1:
                                Header = "जन्म तिथि";
                                Message = "कृपया अपनी जन्मतिथि भरें !";
                                break;
                            default:
                                Header = "Birth Date";
                                Message = "Please enter your birth date !";
                                break;
                        }

                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle(Header);
                        alert.setMessage(Message);
                        alert.setPositiveButton("OK", null);
                        alert.show();

                    } else {

                        /**
                         * Network Work
                         */
                        List<NameValuePair> params = getRequestParams();

                        networkTask.execute(params, Post_URL.URL_REGISTRATION, 1);
                    }
                } else {


                    String Header, Message;

                    switch (PreferencesManager.getInstance().getLanguageValue()) {
                        case 2:
                            Header = "इंटरनेट कनेक्शन ";
                            Message = "आपले इंटरनेट कनेक्शन तपासा आणि पुन्हा प्रयत्न करा  !";
                            break;
                        case 1:
                            Header = "इंटरनेट कनेक्शन";
                            Message = "अपने इंटरनेट कनेक्शन की जाँच करें और पुन: प्रयास करें!";
                            break;
                        default:
                            Header = "Internet Connection";
                            Message = "Please check your internet connection and try again !";
                            break;
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle(Header);
                    alert.setMessage(Message);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }


            }
        });


        EditText_LoginBirthDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(LoginActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                isDateSelected = true;
                                BirthDate = year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth;
                                EditText_LoginBirthDate.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        PreferencesManager.initializeInstance(this);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        setGeoLocationSpinner(PreferencesManager.getInstance().getLanguageValue());

        networkTask = new NetworkTask(LoginActivity.this, LoginActivity.this, false);

        connectionDetector = new ConnectionDetector(this);

    }


    private List<NameValuePair> getRequestParams() {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("name", EditText_LoginName.getText().toString().trim()));
        params.add(new BasicNameValuePair("gender", "" + Spinner_Gender.getSelectedItemPosition()));
        params.add(new BasicNameValuePair("dob", BirthDate));
        params.add(new BasicNameValuePair("mobile", EditText_LoginMobileNo.getText().toString().trim()));
        params.add(new BasicNameValuePair("state", "" + Spinner_State.getSelectedItemPosition()));
        params.add(new BasicNameValuePair("city", "" + Spinner_City.getSelectedItemPosition()));

        return params;
    }


    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private void setMultiLingualData(int languageCode) {

        String[] Label_Text_array;
        ArrayAdapter gender_adapter;
        String LoginHeader = "";
        switch (languageCode) {
            case 2:
                Label_Text_array = getResources().getStringArray(R.array.Marathi_Strings);
                gender_adapter = ArrayAdapter.createFromResource(
                        this, R.array.mh_gender, R.layout.spinner_item);


                break;
            case 1:
                Label_Text_array = getResources().getStringArray(R.array.Hindi_Strings);
                gender_adapter = ArrayAdapter.createFromResource(
                        this, R.array.hn_gender, R.layout.spinner_item);

                break;
            default:
                Label_Text_array = getResources().getStringArray(R.array.English_Strings);
                gender_adapter = ArrayAdapter.createFromResource(
                        this, R.array.en_gender, R.layout.spinner_item);

                break;
        }

        TextView_LabelLoginName.setText(Label_Text_array[0]);
        TextView_LabelLoginMobile.setText(Label_Text_array[1]);
        TextView_LabelState.setText(Label_Text_array[2]);
        TextView_LabelCity.setText(Label_Text_array[3]);
        TextView_LabelGender.setText(Label_Text_array[4]);
        TextView_LabelBirthDate.setText(Label_Text_array[5]);
        TextView_LoginHeading.setText(Label_Text_array[6]);
        Button_Next.setText(Label_Text_array[7]);

        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Gender.setAdapter(gender_adapter);


    }

    /**
     * This method is used for setting States and Cities data for spinners.
     *
     * @throws JSONException
     */
    private void setGeoLocationSpinner(int languageCode) throws JSONException {

        JSONObject GeoLocation = Util.getGeoLocationJson(this, languageCode);

        StateJsonArray = GeoLocation.getJSONArray("States");

        for (int i = 0; i < StateJsonArray.length(); i++) {

            JSONObject State = StateJsonArray.getJSONObject(i);
            StateArrayList.add(State.getString("Name"));

        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_item, StateArrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_State.setAdapter(adapter);

        try {

            JSONObject State = StateJsonArray.getJSONObject(0);

            JSONArray CityJsonArray = State.getJSONArray("Cities");


            for (int i = 0; i < CityJsonArray.length(); i++) {

                JSONObject City = CityJsonArray.getJSONObject(i);
                CityArrayList.add(City.getString("name"));

            }

            adapter = new ArrayAdapter(this,
                    R.layout.spinner_item, CityArrayList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner_City.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
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
        int success = 0;
        try {
            success = serverResponse.getInt(Responce.TAG_SUCCESS);

            if (success == 1) {

                AppConstants.user = new User(serverResponse.getInt(Responce.TAG_USER_ID), EditText_LoginName.getText().toString().trim(), Spinner_Gender.getSelectedItemPosition(), EditText_LoginMobileNo.getText().toString().trim(), TextView_LabelBirthDate.getText().toString(), Spinner_State.getSelectedItemPosition(), Spinner_City.getSelectedItemPosition());

                Gson gson = new Gson();

                // convert java object to JSON format,
                // and returned as JSON formatted string
                String USER_JSON = gson.toJson(AppConstants.user);

                PreferencesManager.getInstance().setUserData(USER_JSON);

                Util.log(TAG, "Registration Successful !");
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }

    }

}
