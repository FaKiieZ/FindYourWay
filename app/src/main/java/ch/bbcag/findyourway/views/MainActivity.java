package ch.bbcag.findyourway.views;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.bbcag.findyourway.R;

/**
 * MainAcitivty für diese Applikation
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  ViewPager with the section adapters
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
        adapter.addFragmnet(new TabSearchFragment(), "");
        adapter.addFragmnet(new TabFavouriteFragment(), "");
        adapter.addFragmnet(new TabHomeFragment(), "");
        viewPager.setAdapter(adapter);
    }
}