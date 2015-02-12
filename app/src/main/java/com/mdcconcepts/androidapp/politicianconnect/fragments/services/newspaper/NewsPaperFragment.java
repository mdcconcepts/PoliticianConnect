package com.mdcconcepts.androidapp.politicianconnect.fragments.services.newspaper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.ExpandableHeightGridView;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewsPaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsPaperFragment extends Fragment implements CompletionListener {

    private static final String TAG = "NewsPaperFragment";

    /**
     * XML Controllers
     */
    /**
     * XML Controllers
     */
    private ExpandableHeightGridView GridView_Marathi_News;
    private ProgressBar ProgressBar_Marathi_NewsLoader;

    private ExpandableHeightGridView GridView_Hindi_News;
    private ProgressBar ProgressBar_Hindi_NewsLoader;

    private ExpandableHeightGridView GridView_English_News;
    private ProgressBar ProgressBar_English_NewsLoader;

    private ArrayList<Newspaper> MarathiNewspaperArrayList = new ArrayList<Newspaper>();
    private ArrayList<Newspaper> HindiNewspaperArrayList = new ArrayList<Newspaper>();
    private ArrayList<Newspaper> EnglishNewspaperArrayList = new ArrayList<Newspaper>();

    private TextView TextView_MarathiNewspaper;
    private TextView TextView_HindiNewspaper;
    private TextView TextView_EnglishNewspaper;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsPaperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsPaperFragment newInstance() {
        return new NewsPaperFragment();
    }

    public NewsPaperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init_params(View rootView) {


        TextView_MarathiNewspaper = (TextView) rootView.findViewById(R.id.TextView_MarathiNewspaper);

        TextView_HindiNewspaper = (TextView) rootView.findViewById(R.id.TextView_HindiNewspaper);

        TextView_EnglishNewspaper = (TextView) rootView.findViewById(R.id.TextView_EnglishNewspaper);

        GridView_Marathi_News = (ExpandableHeightGridView) rootView.findViewById(R.id.GridView_Marathi_News);

        ProgressBar_Marathi_NewsLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_Marathi_NewsLoader);

        GridView_Marathi_News.setExpanded(true);

        GridView_Marathi_News.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MarathiNewspaperArrayList.get(position).getNewspaper_website()));
                startActivity(browserIntent);

            }
        });

        GridView_Hindi_News = (ExpandableHeightGridView) rootView.findViewById(R.id.GridView_Hindi_News);

        ProgressBar_Hindi_NewsLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_Hindi_NewsLoader);

        GridView_Hindi_News.setExpanded(true);

        GridView_Hindi_News.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(HindiNewspaperArrayList.get(position).getNewspaper_website()));
                startActivity(browserIntent);


            }
        });

        GridView_English_News = (ExpandableHeightGridView) rootView.findViewById(R.id.GridView_English_News);

        ProgressBar_English_NewsLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_English_NewsLoader);

        GridView_English_News.setExpanded(true);

        GridView_English_News.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(EnglishNewspaperArrayList.get(position).getNewspaper_website()));
                startActivity(browserIntent);


            }
        });

        NetworkTask networkTask = new NetworkTask(this, getActivity(), false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETNEWSPAPERS, 1);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_paper, container, false);

        init_params(rootView);

        return rootView;

    }

    private List<NameValuePair> getRequestParams() {

        return new ArrayList<NameValuePair>();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

                JSONArray Marathi_Newspaper = serverResponse.getJSONArray("Marathi_Newspaper");

                for (int i = 0; i < Marathi_Newspaper.length(); i++) {
                    MarathiNewspaperArrayList.add(new Newspaper(Marathi_Newspaper.getJSONObject(i).getString("LOGO"), Marathi_Newspaper.getJSONObject(i).getString("WEBSITE")));
                }

                GridView_Marathi_News.setAdapter(new Newspaper_Custom_Adapter(getActivity(), MarathiNewspaperArrayList));

                JSONArray Hindi_Newspaper = serverResponse.getJSONArray("Hindi_Newspaper");

                for (int i = 0; i < Hindi_Newspaper.length(); i++) {
                    HindiNewspaperArrayList.add(new Newspaper(Hindi_Newspaper.getJSONObject(i).getString("LOGO"), Hindi_Newspaper.getJSONObject(i).getString("WEBSITE")));
                }

                GridView_Hindi_News.setAdapter(new Newspaper_Custom_Adapter(getActivity(), HindiNewspaperArrayList));

                Util.log(TAG, "News Paper Received !");

                JSONArray English_Newspaper = serverResponse.getJSONArray("English_Newspaper");

                for (int i = 0; i < Hindi_Newspaper.length(); i++) {
                    EnglishNewspaperArrayList.add(new Newspaper(English_Newspaper.getJSONObject(i).getString("LOGO"), English_Newspaper.getJSONObject(i).getString("WEBSITE")));
                }

                GridView_English_News.setAdapter(new Newspaper_Custom_Adapter(getActivity(), EnglishNewspaperArrayList));

                Util.log(TAG, "News Paper Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        ProgressBar_Marathi_NewsLoader.setVisibility(View.GONE);
        ProgressBar_Hindi_NewsLoader.setVisibility(View.GONE);
        ProgressBar_English_NewsLoader.setVisibility(View.GONE);
    }

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private void setMultiLingualData(int languageCode) {

        String[] Label_Text_array;
        switch (languageCode) {
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

        TextView_MarathiNewspaper.setText(Label_Text_array[16]);

        TextView_HindiNewspaper.setText(Label_Text_array[17]);

        TextView_EnglishNewspaper.setText(Label_Text_array[18]);


    }

}
