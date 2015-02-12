package com.mdcconcepts.androidapp.politicianconnect.fragments.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.LoginActivity;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.CompletionListener;
import com.mdcconcepts.androidapp.politicianconnect.common.network.NetworkTask;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Responce;
import com.mdcconcepts.androidapp.politicianconnect.common.util.Util;
import com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect.UserConnectActivity;
import com.mdcconcepts.androidapp.politicianconnect.gallary.GalleryActivity;
import com.mdcconcepts.androidapp.politicianconnect.politician.MLAContactActivity;
import com.mdcconcepts.androidapp.politicianconnect.politician.OperationsActivity;
import com.mdcconcepts.androidapp.politicianconnect.politician.PoliticianInformationActivity;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * {@link com.mdcconcepts.androidapp.politicianconnect.fragments.home.HomeFragment}
 * Created by CODELORD on 2/4/2015.
 *
 * @author CODELORD
 */
public class HomeFragment extends Fragment implements CompletionListener {

    private static final String TAG = "HomeFragment";

    /**
     * XML Controllers
     */
    private ViewPager mViewPager;
    private Button button_brief_info;
    private Button button_my_aim;
    private Button button_image_gallary;
    private Button button_video_gallary;
    private Button button_news;
    private Button button_project;
    private Button button_message;
    private Button button_communication;
    private ProgressBar slider_loader;

    private ArrayList<String> Slider_URL = new ArrayList<String>();


    CustomPagerAdapter mCustomPagerAdapter;

    private int NUM_PAGES = 5;
    private int currentPage;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());

        /**
         * Controller Init
         */
        button_brief_info = (Button) rootView.findViewById(R.id.button_brief_info);
        button_my_aim = (Button) rootView.findViewById(R.id.button_my_aim);
        button_image_gallary = (Button) rootView.findViewById(R.id.button_image_gallary);
        button_video_gallary = (Button) rootView.findViewById(R.id.button_video_gallary);
        button_news = (Button) rootView.findViewById(R.id.button_news);
        button_project = (Button) rootView.findViewById(R.id.button_project);
        button_message = (Button) rootView.findViewById(R.id.button_message);
        button_communication = (Button) rootView.findViewById(R.id.button_communication);

        slider_loader = (ProgressBar) rootView.findViewById(R.id.slider_loader);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        setMultiLingualData(PreferencesManager.getInstance().getLanguageValue());

        timer();

        button_image_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                Bundle b = new Bundle();
                b.putInt("media", 0);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        button_video_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                Bundle b = new Bundle();
                b.putInt("media", 1);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        button_my_aim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PoliticianInformationActivity.class);
                intent.putExtra("is_aim", 1);
                startActivity(intent);
            }
        });

        button_brief_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PoliticianInformationActivity.class);
                intent.putExtra("is_aim", 0);
                startActivity(intent);
            }
        });

        button_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OperationsActivity.class);
                intent.putExtra("is_project", 1);
                startActivity(intent);
            }
        });

        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OperationsActivity.class);
                intent.putExtra("is_project", 0);
                startActivity(intent);
            }
        });

        button_communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserConnectActivity.class);
                startActivity(intent);
            }
        });

        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MLAContactActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void timer() {
        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 100, 5000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            init_params(activity);
        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
//        ((HomeActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    /**
     * This method is used to initialise all variables going to used in the Activity {@link LoginActivity}
     *
     * @throws JSONException
     */
    private void init_params(Activity activity) throws JSONException {


        //  Network Variable
        NetworkTask networkTask = new NetworkTask(this, activity, false);

        List<NameValuePair> params = getRequestParams();

        networkTask.execute(params, Post_URL.URL_GETSLIDER, 1);

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

        button_brief_info.setText(Label_Text_array[8]);
        button_my_aim.setText(Label_Text_array[9]);
        button_image_gallary.setText(Label_Text_array[10]);
        button_video_gallary.setText(Label_Text_array[11]);
        button_news.setText(Label_Text_array[12]);
        button_project.setText(Label_Text_array[13]);
        button_message.setText(Label_Text_array[15]);
        button_communication.setText(Label_Text_array[14]);


    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Slider_URL.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            String url = Post_URL.DOMAIN + Slider_URL.get(position);
            Picasso.with(getActivity())
                    .load(url)
                    .into(imageView);

//            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private List<NameValuePair> getRequestParams() {
        // TODO Enter Comments Here ...
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        return params;
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

                JSONArray Slider = serverResponse.getJSONArray("Slider");

                for (int i = 0; i < Slider.length(); i++) {
                    Slider_URL.add(Slider.getJSONObject(i).getString("URL"));
                }

                mViewPager.setAdapter(mCustomPagerAdapter);

                Util.log(TAG, "Slider Images Received !");


            } else {
                Util.log(TAG, serverResponse.getString(Responce.TAG_ERROR));

            }

        } catch (JSONException e) {
            Util.log(TAG, e.getMessage());
        }
        slider_loader.setVisibility(View.GONE);
    }

}