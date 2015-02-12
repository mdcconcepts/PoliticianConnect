package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;


import android.content.Context;
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


public class Message_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    private View rootView;
    private ArrayList<MessageContainer> mMessageContainerList;
    private String MLA_photo;

    public Message_Custom_Adapter(Context c, ArrayList<MessageContainer> mMessageContainerList, String MLA_photo) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMessageContainerList = mMessageContainerList;
        this.MLA_photo = MLA_photo;
    }

    @Override
    public int getCount() {
        return mMessageContainerList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageContainerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageContainer messageContainer = mMessageContainerList.get(position);

        rootView = mLayoutInfater.inflate(R.layout.message_list_custom_item, null);

        final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

        final TextView EditText_ContactSubject = (TextView) rootView.findViewById(R.id.EditText_ContactSubject);

        final TextView TextView_Date = (TextView) rootView.findViewById(R.id.TextView_Date);

        EditText_ContactSubject.setText(messageContainer.getSubject());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(messageContainer.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView_Date.setText(new SimpleDateFormat("dd MMM yyyy").format(date));

        String url;

        url = Post_URL.DOMAIN + MLA_photo;

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
