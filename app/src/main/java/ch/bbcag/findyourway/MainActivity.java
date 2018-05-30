package ch.bbcag.findyourway;

import android.app.TabActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.bbcag.findyourway.views.PagerAdapter;
import ch.bbcag.findyourway.views.TabFavouriteFragment;
import ch.bbcag.findyourway.views.TabHomeFragment;
import ch.bbcag.findyourway.views.TabSearchFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG  = "MainActivity";
    private PagerAdapter pagerAdapter;
    private ViewPager mViewPager;
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
    }

    private void setupViewPager(ViewPager viewPager){
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        // add the tabs
        adapter.addFragmnet(new TabSearchFragment(), "@drawable/ic_search_black_24dp");
        adapter.addFragmnet(new TabFavouriteFragment(), "Favourite");
        adapter.addFragmnet(new TabHomeFragment(), "Home");
        viewPager.setAdapter(adapter);
    }

}
