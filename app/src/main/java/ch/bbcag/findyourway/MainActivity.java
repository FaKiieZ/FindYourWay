package ch.bbcag.findyourway;

import android.app.TabActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.bbcag.findyourway.views.PagerAdapter;
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
        adapter.addFragmnet(new TabSearchFragment(), "Search");
        adapter.addFragmnet(new TabSearchFragment(), "Favourite");
        viewPager.setAdapter(adapter);
    }

}
