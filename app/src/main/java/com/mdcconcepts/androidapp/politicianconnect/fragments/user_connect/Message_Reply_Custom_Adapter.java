package com.mdcconcepts.androidapp.politicianconnect.fragments.user_connect;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Message_Reply_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    private View rootView;
    private ArrayList<ReplyContainer> mReplyContainersList;

    public Message_Reply_Custom_Adapter(Context c, ArrayList<ReplyContainer> mReplyContainersList) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mReplyContainersList = mReplyContainersList;
    }

    @Override
    public int getCount() {
        return mReplyContainersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mReplyContainersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReplyContainer replyContainer = mReplyContainersList.get(position);

        if (replyContainer.getReply_by() == 0) {
            rootView = mLayoutInfater.inflate(R.layout.message_mla_chat_list_custom_item, null);
        } else {
            rootView = mLayoutInfater.inflate(R.layout.message_user_chat_list_custom_item, null);
        }
        final TextView TextView_Message = (TextView) rootView.findViewById(R.id.TextView_Message);

        final TextView TextView_Date = (TextView) rootView.findViewById(R.id.TextView_Date);
//
        TextView_Message.setText(replyContainer.getReply().replace("<br>", "\n"));

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(replyContainer.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView_Date.setText(new SimpleDateFormat("dd MMM yyyy HH:mm").format(date));

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
