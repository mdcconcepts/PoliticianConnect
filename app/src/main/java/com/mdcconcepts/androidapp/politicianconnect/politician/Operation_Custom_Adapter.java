package com.mdcconcepts.androidapp.politicianconnect.politician;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Operation_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater mLayoutInfater;
    View rootView;
    private ArrayList<OperationContainer> operationContainers;

    public Operation_Custom_Adapter(Context c, ArrayList<OperationContainer> operationContainers) {
        mContext = c;
        mLayoutInfater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.operationContainers = operationContainers;
    }

    @Override
    public int getCount() {
        return operationContainers.size();
    }

    @Override
    public Object getItem(int position) {
        return operationContainers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rootView = mLayoutInfater.inflate(R.layout.operation_grid_item, null);

        final ImageView ImageView_Photo = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

        TextView TextView_Place = (TextView) rootView.findViewById(R.id.TextView_Place);

        TextView TextView_Time = (TextView) rootView.findViewById(R.id.TextView_Time);

        TextView TextView_Title = (TextView) rootView.findViewById(R.id.TextView_Title);

        final TextView TextView_Desr = (TextView) rootView.findViewById(R.id.TextView_Desr);

        final TextView TextView_View = (TextView) rootView.findViewById(R.id.TextView_View);

        OperationContainer operationContainer = operationContainers.get(position);


        String url = Post_URL.DOMAIN + operationContainer.getImg();
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

        TextView_Place.setText(operationContainer.getPlace());

        TextView_Time.setText(operationContainer.getTime());

        TextView_Title.setText(operationContainer.getTitle());

        String Desr = operationContainer.getDesr();

        TextView_Desr.setText(Desr.substring(0, Math.min(Desr.length(), 50)) + " ...");

        TextView_View.setText(setMultiLingualData(PreferencesManager.getInstance().getLanguageValue()));

        TextView_View.setTag(position);

        TextView_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer position = (Integer) view.getTag();
                TextView_Desr.setText(operationContainers.get(position).getDesr());

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

}
