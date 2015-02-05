package com.mdcconcepts.androidapp.politicianconnect.gallary;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mdcconcepts.androidapp.politicianconnect.R;
import com.mdcconcepts.androidapp.politicianconnect.common.network.Post_URL;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class GalleryViewActivity extends ActionBarActivity {

    /**
     * XML Controller
     */
    ImageView ImageView_Viewer;
    ProgressBar ProgressBar_ImageViewLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        Bundle b = getIntent().getExtras();
        String Image_URL = b.getString("Image_URL");

        if (Image_URL.length() == 0)
            finish();
        restoreActionBar();

        ImageView_Viewer = (ImageView) findViewById(R.id.ImageView_Viewer);
        ProgressBar_ImageViewLoader = (ProgressBar) findViewById(R.id.ProgressBar_ImageViewLoader);

        String url = Post_URL.DOMAIN + Image_URL;

        Picasso.with(this)
                .load(url)
                .into(ImageView_Viewer, new Callback() {

                    @Override
                    public void onSuccess() {
                        ImageView_Viewer.setVisibility(View.VISIBLE);
                        ProgressBar_ImageViewLoader.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        ProgressBar_ImageViewLoader.setVisibility(View.VISIBLE);
                        ImageView_Viewer.setVisibility(View.INVISIBLE);
                    }
                });

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
        actionBar.setTitle("Image View");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
