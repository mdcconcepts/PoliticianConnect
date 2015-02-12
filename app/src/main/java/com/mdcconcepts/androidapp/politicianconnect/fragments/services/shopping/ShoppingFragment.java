package com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping.sub_shopping.ShoppingProduct;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShoppingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingFragment extends Fragment implements CompletionListener {

    private static final String TAG = "ServiceFragment";

    private ArrayList<ShoppingContainer> ShoppingArrayList = new ArrayList<ShoppingContainer>();

    /**
     * XML Controllers
     */
    /**
     * XML Controllers
     */
    private GridView GridView_Service;
    private ProgressBar ProgressBar_ServiceLoader;


    private TextView TextView_ServiceName;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingFragment newInstance() {
        ShoppingFragment fragment = new ShoppingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping, container, false);

        init_params(rootView);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init_params(View rootView) {


        TextView_ServiceName = (TextView) rootView.findViewById(R.id.TextView_ServiceName);


        GridView_Service = (GridView) rootView.findViewById(R.id.GridView_Service);

        ProgressBar_ServiceLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ServiceLoader);

        GridView_Service.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(getActivity(), ShoppingProduct.class);
                myIntent.putExtra("shoppingid", ShoppingArrayList.get(position).getId());
                myIntent.putExtra("shopping_title", ShoppingArrayList.get(position).getTitle());
                startActivity(myIntent);
            }
        });

        NetworkTask networkTask = new NetworkTask(this, getActivity(), false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETSHOPPING, 1);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        // nav drawer icons from resources
        String[] navMenuIcons = setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        TextView_ServiceName.setText(navMenuIcons[10]);

    }

    private List<NameValuePair> getRequestParams() {
        // TODO Make Documentation

        return new ArrayList<NameValuePair>();
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

                JSONArray Service = serverResponse.getJSONArray("Shopping");

                for (int i = 0; i < Service.length(); i++) {

                    ShoppingArrayList.add(new ShoppingContainer(Integer.parseInt(Service.getJSONObject(i).getString("ID")), Service.getJSONObject(i).getString("TITLE"), Service.getJSONObject(i).getString("LOGO")));
                }

                GridView_Service.setAdapter(new Shopping_Custom_Adapter(getActivity(), ShoppingArrayList));


                Util.log(TAG, "News Paper Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_ServiceLoader.setVisibility(View.GONE);
    }

}
