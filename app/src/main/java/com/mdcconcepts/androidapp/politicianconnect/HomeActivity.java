package com.mdcconcepts.androidapp.politicianconnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mdcconcepts.androidapp.politicianconnect.common.SettingActivity;
import com.mdcconcepts.androidapp.politicianconnect.common.custom.PreferencesManager;
import com.mdcconcepts.androidapp.politicianconnect.fragments.home.HomeFragment;
import com.mdcconcepts.androidapp.politicianconnect.fragments.services.ServiceFragment;
import com.mdcconcepts.androidapp.politicianconnect.fragments.services.newspaper.NewsPaperFragment;
import com.mdcconcepts.androidapp.politicianconnect.fragments.services.shopping.ShoppingFragment;
import com.mdcconcepts.androidapp.politicianconnect.politician.MessageActivity;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * Social Connects
     */
    ImageView Facebook, Youtube, LinkedIn, Tweeter;
    private boolean mdoubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        Facebook = (ImageView) findViewById(R.id.Image_Facebook);
        Youtube = (ImageView) findViewById(R.id.Image_Youtube);
        LinkedIn = (ImageView) findViewById(R.id.Image_Linkedin);
        Tweeter = (ImageView) findViewById(R.id.Image_Tweeter);

        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "fb://page/551877531496760";
                    Intent intent_facebook = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent_facebook);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/NaMoPMo")));
                }
            }
        });
        Youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent_youtube = new Intent(Intent.ACTION_VIEW);
                    intent_youtube.setPackage("com.google.android.youtube");
                    intent_youtube.setData(Uri.parse("https://www.youtube.com/user/narendramodi"));
                    startActivity(intent_youtube);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/narendramodi")));
                }
            }
        });
        LinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent_linked = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/" + "274826784"));
                    startActivity(intent_linked);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/in/narendramodi")));
                }
            }
        });
        Tweeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String twitter_url = "twitter://user?user_id=" + "471741741";
                    Intent intent_twitter = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter_url));
                    startActivity(intent_twitter);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/pmoindia")));
                }
            }
        });

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ServiceFragment.newInstance(9, 2, 1))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NewsPaperFragment.newInstance())
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ServiceFragment.newInstance(3, 0, 3))
                        .commit();
                break;
            case 4:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.irctc.co.in/eticketing/loginHome.jsf"));
                startActivity(browserIntent);
                break;
            case 5:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.airindia.in"));
                startActivity(browserIntent);
                break;
            case 6:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.busindia.com/bus/"));
                startActivity(browserIntent);
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ServiceFragment.newInstance(2, 0, 7))
                        .commit();
                break;
            case 8:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ServiceFragment.newInstance(1, 0, 8))
                        .commit();
                break;
            case 9:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mahabhulekh.maharashtra.gov.in"));
                startActivity(browserIntent);
                break;
            case 10:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ShoppingFragment.newInstance())
                        .commit();
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        Drawable d = getResources().getDrawable(R.drawable.actionbar_bg);
        actionBar.setBackgroundDrawable(d);
        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_menu));
//        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_message) {

            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_share) {
            String shareBody = "https://play.google.com/store/apps/details?id=org.mdcconcepts.opinion_desk.opinion_desk";

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onBackPressed() {
        if (mdoubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.mdoubleBackToExitPressedOnce = true;

        String Message;

        switch (PreferencesManager.getInstance().getLanguageValue()) {
            case 2:
                Message = "बाहेर पडण्यासाठी परत क्लिक करा";
                break;
            case 1:
                Message = "बाहर निकलने के लिए फिर से वापस क्लिक करें";
                break;
            default:
                Message = "Please click BACK again to exit";
                break;
        }

        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mdoubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
