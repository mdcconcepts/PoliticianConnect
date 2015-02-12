package com.mdcconcepts.androidapp.politicianconnect.fragments.services;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.mdcconcepts.androidapp.politicianconnect.fragments.services.subservice.SubServiceActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Service_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    View rootView;
    private ArrayList<ServiceContainer> ServiceArrayList;

    public Service_Custom_Adapter(Context c, ArrayList<ServiceContainer> ServiceArrayList) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ServiceArrayList = ServiceArrayList;
    }

    @Override
    public int getCount() {
        return ServiceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ServiceArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ServiceContainer serviceContainer = ServiceArrayList.get(position);

        if (serviceContainer.getService() != 9) {
            rootView = mLayoutInfater.inflate(R.layout.service_grid_item, null);

            final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

            final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

            final TextView TextView_ServiceTitle = (TextView) rootView.findViewById(R.id.TextView_ServiceTitle);

            TextView_ServiceTitle.setVisibility(View.GONE);

            Button Button_Action = (Button) rootView.findViewById(R.id.Button_Action);


            switch (serviceContainer.getService_category()) {
                case 0:
                    Button_Action.setText(setMultiLingualData(PreferencesManager.getInstance().getLanguageValue(), serviceContainer.getService_category()));
                    if (serviceContainer.getService() == 1) {
                        TextView_ServiceTitle.setVisibility(View.VISIBLE);

                        TextView_ServiceTitle.setText(serviceContainer.getTitle());
                    }
                    break;
                case 1:
                    Button_Action.setText(setMultiLingualData(PreferencesManager.getInstance().getLanguageValue(), serviceContainer.getService_category()));
                    TextView_ServiceTitle.setVisibility(View.VISIBLE);

                    TextView_ServiceTitle.setText(serviceContainer.getTitle());
                    break;
            }

            String url;

            url = Post_URL.DOMAIN + serviceContainer.getLogo();


            Picasso.with(mContext)
                    .load(url)
                    .into(Gallery_Image, new Callback() {

                        @Override
                        public void onSuccess() {
                            Gallery_Image.setVisibility(View.VISIBLE);
                            ProgressBar_ImageLoader.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            ProgressBar_ImageLoader.setVisibility(View.VISIBLE);
                            Gallery_Image.setVisibility(View.INVISIBLE);
                        }
                    });

            Button_Action.setTag(position);
            Button_Action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Integer position = (Integer) view.getTag();

                    ServiceContainer serviceContainer = ServiceArrayList.get(position);

                    switch (serviceContainer.getService_category()) {
                        case 0:
                            String contact_no = serviceContainer.getContact();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + contact_no));
                            mContext.startActivity(callIntent);
                            break;
                        case 1:
                            Intent myIntent = new Intent(mContext, SubServiceActivity.class);
                            myIntent.putExtra("productid", ServiceArrayList.get(position).getService());
                            myIntent.putExtra("prodcut_title", ServiceArrayList.get(position).getTitle());
                            mContext.startActivity(myIntent);
                            break;
                    }
                }
            });
        } else {
            rootView = mLayoutInfater.inflate(R.layout.govt_office_service_grid_item, null);

            final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

            final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

            final TextView TextView_ServiceTitle = (TextView) rootView.findViewById(R.id.TextView_ServiceTitle);

            String url;

            url = Post_URL.DOMAIN + serviceContainer.getLogo();

            TextView_ServiceTitle.setText(serviceContainer.getTitle());
            Picasso.with(mContext)
                    .load(url)
                    .into(Gallery_Image, new Callback() {

                        @Override
                        public void onSuccess() {
                            Gallery_Image.setVisibility(View.VISIBLE);
                            ProgressBar_ImageLoader.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            ProgressBar_ImageLoader.setVisibility(View.VISIBLE);
                            Gallery_Image.setVisibility(View.INVISIBLE);
                        }
                    });


        }
        return rootView;
    }

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private String setMultiLingualData(int languageCode, int isProduct) {

        String[] Label_Text_array;
        switch (languageCode) {
            case 2:
                Label_Text_array = mContext.getResources().getStringArray(R.array.Marathi_Strings);
                break;
            case 1:
                Label_Text_array = mContext.getResources().getStringArray(R.array.Hindi_Strings);

                break;
            default:
                Label_Text_array = mContext.getResources().getStringArray(R.array.English_Strings);

                break;
        }

        if (isProduct == 1) {
            return Label_Text_array[20];
        }

        return Label_Text_array[19];

    }

}
