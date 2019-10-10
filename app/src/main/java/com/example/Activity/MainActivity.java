package com.example.Activity;


import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;;
import com.example.Adapter.FragmentAdapter;
import com.example.Fragment.HomeFragment;
import com.example.Fragment.MineFragment;
import com.example.testsys.R;



public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
	    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);


	    List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());


	    String [] titles = new String [] {"首页","我的"};

	//设置适配器
	    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
		viewPager.setAdapter(adapter);

	//绑定
		tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++) {
			TabLayout.Tab tab = tabLayout.getTabAt(i);
			Drawable d = null;
			switch (i) {
				case 0:
					d = getResources().getDrawable(R.drawable.tab_home);
					break;
				case 1:
					d = getResources().getDrawable(R.drawable.tab_mine);
					break;
			}
			tab.setIcon(d);
		}
	}
}
