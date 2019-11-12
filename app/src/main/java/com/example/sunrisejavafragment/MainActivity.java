package com.example.sunrisejavafragment;

import android.net.Uri;
import android.os.Bundle;

import com.example.sunrisejavafragment.ui.main.HomeFragment;
import com.example.sunrisejavafragment.ui.main.SelectRange;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.sunrisejavafragment.ui.main.SectionsPagerAdapter;

//ensure that the interface is implemented
public class MainActivity extends AppCompatActivity implements SelectRange.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener {

    private int previous_tab = 0;
    private FragmentViewModel model;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        /*Toolbar share_button = findViewById(R.id.share_button);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                HomeFragment fragment = (HomeFragment) ((FragmentPagerAdapter)viewPager.getAdapter()).getItem(0);
                sendIntent.putExtra(Intent.EXTRA_TEXT, fragment.getShareString(selected_location));

                System.out.println(fragment.getShareString(selected_location));

                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });*/

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment fragment = ((FragmentPagerAdapter)viewPager.getAdapter()).getItem(position);
                fragment.onResume();
            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

    }



    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}