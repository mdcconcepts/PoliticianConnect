package com.mdcconcepts.androidapp.politicianconnect.gallary;


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


public class Image_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    View rootView;
    private ArrayList<String> Gallery_Images;
    private int media;

    public Image_Custom_Adapter(Context c, ArrayList<String> Gallery_Images, int media) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.Gallery_Images = Gallery_Images;
        this.media = media;
    }

    @Override
    public int getCount() {
        return Gallery_Images.size();
    }

    @Override
    public Object getItem(int position) {
        return Gallery_Images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rootView = mLayoutInfater.inflate(R.layout.photo_gallery_grid_item, null);

        final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

        ImageView ImageView_youtube_watermark = (ImageView) rootView.findViewById(R.id.ImageView_youtube_watermark);


        String url;

        switch (media) {
            case 0:
                url = Post_URL.DOMAIN + Gallery_Images.get(position);
                ImageView_youtube_watermark.setVisibility(View.GONE);
                break;
            default:
                url = "http://img.youtube.com/vi/" + Gallery_Images.get(position) + "/0.jpg";
                break;
        }


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
