package net.lzzy.radio;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ChartFragment.OnFragmentInteractionListener, View.OnClickListener {
    private int[] tabIds = {R.id.tab1,R.id.tab2,R.id.tab3};

    private List<Fragment> fragments;
    private ImageButton img1;
    private TextView tv1;
    private ImageButton img2;
    private TextView tv2;
    private ImageButton img3;
    private TextView tv3;
    private int colorNormal;
    private int colorPressed;
    private StaticViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fragments = new ArrayList<>();
        fragments.add(new LocalFragment());
        fragments.add(new FindFragment());
        fragments.add(new ChartFragment());
        pager = findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                selectTab(tabIds[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initViews() {
        img1 = findViewById(R.id.btn1);
        tv1 = findViewById(R.id.tv1);
        img2 = findViewById(R.id.btn2);
        tv2 = findViewById(R.id.tv2);
        img3 = findViewById(R.id.btn3);
        tv3 = findViewById(R.id.tv3);
        colorNormal = ContextCompat.getColor(this, R.color.iconNormal);
        colorPressed = ContextCompat.getColor(this, android.R.color.white);
        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
    }

    private void selectTab(int tabId){
        img1.setImageResource(R.drawable.ic_local_normal);
        img2.setImageResource(R.drawable.ic_find_normal);
        img3.setImageResource(R.drawable.ic_chart_normal);
        tv1.setTextColor(colorNormal);
        tv2.setTextColor(colorNormal);
        tv3.setTextColor(colorNormal);
        switch (tabId){
            case R.id.tab1:
                img1.setImageResource(R.drawable.ic_local_pressed);
                tv1.setTextColor(colorPressed);
                pager.setCurrentItem(0);
                break;
            case R.id.tab2:
                img2.setImageResource(R.drawable.ic_find_pressed);
                tv2.setTextColor(colorPressed);
                pager.setCurrentItem(1);
                break;
            case R.id.tab3:
                img3.setImageResource(R.drawable.ic_chart_pressed);
                tv3.setTextColor(colorPressed);
                pager.setCurrentItem(2);
                break;
            default:
                //aaaaaaa
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        selectTab(v.getId());
    }
}
