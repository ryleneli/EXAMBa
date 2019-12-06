package com.example.Activity;


import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;;
import com.example.Adapter.FragmentAdapter;
import com.example.Constant;
import com.example.DBControl.DBAdapter;
import com.example.DBControl.DataTrans;
import com.example.Fragment.HomeFragment;
import com.example.Fragment.MineFragment;
import com.example.Utils.LogUtil;
import com.example.Utils.StatusBarUtil;
import com.example.testsys.R;

import static com.example.DBControl.DBAdapter.DATABSE_TABLE;


public class MainActivity extends AppCompatActivity {
	private static String TAG = "LRLMainActivity ";
    private Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/**
 * 更改状态栏颜色

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().setStatusBarColor(getResources().getColor(R.color.colorTitleBar));
*/      setContentView(R.layout.activity_main);
		//toolbar = (Toolbar) findViewById(R.id.toolbar1);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

		StatusBarUtil.setRootViewFitsSystemWindows(this,false);
		//设置状态栏透明
		StatusBarUtil.setTranslucentStatus(this);

	    DBAdapter dbAdapter = new DBAdapter(this);
	    if (  !(DBAdapter.HaveData(dbAdapter.open(),DATABSE_TABLE)) ) {
			new DataTrans(this).Trans(this);
			LogUtil.i(this);
		}
	    List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());


	    String [] titles = new String [] {Constant.HOME,Constant.MINE};

	//设置适配器
	    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
		viewPager.setAdapter(adapter);
		//viewPager.setOffscreenPageLimit(2);
		//绑定
		tabLayout.setupWithViewPager(viewPager);//tablayout和viewpager联动

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
			@Override
			public void onPageSelected(int position) {
				if (position == 0)
					StatusBarUtil.setStatusBarDarkTheme(MainActivity.this,false);
				else
					StatusBarUtil.setStatusBarDarkTheme(MainActivity.this,true);
			}
			@Override
			public void onPageScrollStateChanged(int position) {}
		});
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
