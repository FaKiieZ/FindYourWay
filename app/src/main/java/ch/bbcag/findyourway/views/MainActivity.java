package ch.bbcag.findyourway.views;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.FavouriteDataSource;
import ch.bbcag.findyourway.helper.CurrentLocationListener;
import ch.bbcag.findyourway.helper.FavouriteDbHelper;
import ch.bbcag.findyourway.model.Location;
import ch.bbcag.findyourway.views.PagerAdapter;
import ch.bbcag.findyourway.views.TabFavouriteFragment;
import ch.bbcag.findyourway.views.TabHomeFragment;
import ch.bbcag.findyourway.views.TabSearchFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG  = "MainActivity";
    private PagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private FavouriteDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        //  ViewPager with the section adapters
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));

        // set icons for the tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search_black_24dp); // icon for start tab
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star_black_24dp); // icon for the favourite tab
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home_black_24dp); // icon for the home tab

    }

    private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        // add the tabs
        // title is empty, because icons are used
        adapter.addFragmnet(new TabSearchFragment(), "");
        adapter.addFragmnet(new TabFavouriteFragment(), "");
        adapter.addFragmnet(new TabHomeFragment(), "");
        viewPager.setAdapter(adapter);
    }
}
