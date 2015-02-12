package com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping;


import android.content.Context;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Shopping_Custom_Adapter extends BaseAdapter {

    private static final String TAG = "Image_Custom_Adapter";

    private Context mContext;
    private LayoutInflater layoutInflater;
    View rootView;
    private ArrayList<ShoppingContainer> ShoppingArrayList;

    public Shopping_Custom_Adapter(Context c, ArrayList<ShoppingContainer> ShoppingArrayList) {
        mContext = c;
        layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ShoppingArrayList = ShoppingArrayList;
    }

    @Override
    public int getCount() {
        return ShoppingArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ShoppingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        rootView = layoutInflater.inflate(R.layout.service_grid_item, null);

        final ImageView Gallery_Image = (ImageView) rootView.findViewById(R.id.ImageView_Photo);

        final ProgressBar ProgressBar_ImageLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_ImageLoader);

        final TextView TextView_ServiceTitle = (TextView) rootView.findViewById(R.id.TextView_ServiceTitle);

        Button Button_Action = (Button) rootView.findViewById(R.id.Button_Action);

        ShoppingContainer shoppingContainer = ShoppingArrayList.get(position);

        Button_Action.setText(setMultiLingualData(PreferencesManager.getInstance().getLanguageValue()));

        TextView_ServiceTitle.setVisibility(View.VISIBLE);

        Button_Action.setVisibility(View.GONE);

        TextView_ServiceTitle.setText(shoppingContainer.getTitle());

        String url;

        url = Post_URL.DOMAIN + shoppingContainer.getLogo();

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

//                ServiceContainer serviceContainer = ShoppingArrayList.get(position);
//
//                switch (serviceContainer.getService_category()) {
//                    case 0:
//                        String contact_no = serviceContainer.getContact();
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:" + contact_no));
//                        mContext.startActivity(callIntent);
//                        break;
//                    case 1:
//                        break;
//                }
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

        return Label_Text_array[19];

    }

}
