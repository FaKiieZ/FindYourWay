package ch.bbcag.findyourway.views;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.FavouriteDbHelper;
import ch.bbcag.findyourway.helper.HomeDbHelper;

/**
 * MainAcitivty für diese Applikation
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    private TabSearchFragment tabSearchFragment = new TabSearchFragment();
    private TabFavouriteFragment tabFavouriteFragment = new TabFavouriteFragment();
    private TabHomeFragment tabHomeFragment = new TabHomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  ViewPager with the section adapters
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));

        // Setzt die Icons für die Tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search_black_24dp); // icon for start tab
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star_black_24dp); // icon for the favourite tab
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home_black_24dp); // icon for the home tab
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        // add the tabs
        // title is empty, because icons are used
        adapter.addFragmnet(tabSearchFragment, "");
        adapter.addFragmnet(tabFavouriteFragment, "");
        adapter.addFragmnet(tabHomeFragment, "");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        tabSearchFragment.locationPermissionGranted = false;
        switch (requestCode) {
            case TabSearchFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tabSearchFragment.locationPermissionGranted = true;
                    tabSearchFragment.updateLocationUI();
                    tabSearchFragment.getDeviceLocation(true);
                }

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "FindYourWay braucht die Berechtigung für den Standort des Gerätes!", Toast.LENGTH_LONG).show();
                    finishAndRemoveTask();
                }
            }
        }
    }
}