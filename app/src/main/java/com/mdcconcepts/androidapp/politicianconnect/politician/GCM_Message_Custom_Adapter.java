package com.mdcconcepts.androidapp.politicianconnect.politician;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GCM_Message_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "GCM_Message_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    View rootView;
    private ArrayList<GCMMessageContainer> gcmMessageContainersArrayList;

    public GCM_Message_Custom_Adapter(Context c, ArrayList<GCMMessageContainer> gcmMessageContainersArrayList) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.gcmMessageContainersArrayList = gcmMessageContainersArrayList;
    }

    @Override
    public int getCount() {
        return gcmMessageContainersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gcmMessageContainersArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rootView = mLayoutInfater.inflate(R.layout.row_gcm_message, null);

        final ImageView ImageView_Photo = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ImageView ImageView_youtube_watermark = (ImageView) rootView.findViewById(R.id.ImageView_youtube_watermark);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

        TextView TextView_Time = (TextView) rootView.findViewById(R.id.TextView_Time);

        TextView TextView_Title = (TextView) rootView.findViewById(R.id.TextView_Title);

        TextView TextView_Desr = (TextView) rootView.findViewById(R.id.TextView_Desr);

        GCMMessageContainer gcmMessageContainer = gcmMessageContainersArrayList.get(position);

        ImageView_youtube_watermark.setVisibility(View.GONE);
        String url = "";
        if (gcmMessageContainer.getMedia_type().equals("image")) {
            url = Post_URL.DOMAIN + gcmMessageContainer.getMedia_url();
        } else if (gcmMessageContainer.getMedia_type().equals("video")) {
            ImageView_youtube_watermark.setVisibility(View.VISIBLE);
            url = "http://img.youtube.com/vi/" + extractVedioId(gcmMessageContainer.getMedia_url()) + "/0.jpg";
        }
        Picasso.with(mContext)
                .load(url)
                .into(ImageView_Photo, new Callback() {

                    @Override
                    public void onSuccess() {
                        ImageView_Photo.setVisibility(View.VISIBLE);
                        ProgressBar_ImageLoader.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        ProgressBar_ImageLoader.setVisibility(View.VISIBLE);
                        ImageView_Photo.setVisibility(View.INVISIBLE);
                    }
                });


        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(gcmMessageContainer.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView_Time.setText(new SimpleDateFormat("dd MMM yyyy").format(date));


        TextView_Title.setText(gcmMessageContainer.getTitle());

        String Desr = gcmMessageContainer.getMessage();

        TextView_Desr.setText(Desr);


        ImageView_Photo.setTag(position);

        ImageView_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer position = (Integer) view.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + extractVedioId(gcmMessageContainersArrayList.get(position).getMedia_url())));
                mContext.startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * This method is used for setting up Custom Labels.
     *
     * @param languageCode This decide which language will be displays on layout.
     */
    private String setMultiLingualData(int languageCode) {

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

        return Label_Text_array[23];

    }

    private String extractVedioId(String video) {

        return video.replace("/politician/", "");
    }
}
