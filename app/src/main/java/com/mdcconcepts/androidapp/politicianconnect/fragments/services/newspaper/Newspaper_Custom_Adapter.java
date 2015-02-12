package com.mdcconcepts.androidapp.politicianconnect.fragments.services.newspaper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Newspaper_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    View rootView;
    private ArrayList<Newspaper> NewspaperArrayList;

    public Newspaper_Custom_Adapter(Context c, ArrayList<Newspaper> NewspaperArrayList) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.NewspaperArrayList = NewspaperArrayList;
    }

    @Override
    public int getCount() {
        return NewspaperArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return NewspaperArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rootView = mLayoutInfater.inflate(R.layout.newspaper_grid_item, null);

        final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);


        String url;
        url = Post_URL.DOMAIN + NewspaperArrayList.get(position).getNewspaper_logo();


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

        return rootView;
    }
}
