package com.example.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by DELL on 2019/10/3.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private static String TAG = "FragmentAdapter";

        private List<Fragment> mFragmentList;
        private String [] titles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
            super(fm);
            this.mFragmentList = mFragmentList;
        }

        /**
         * titles是给TabLayout设置title用的
         * @param fm
         * @param mFragmentList
         * @param titles
         */
        public FragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList, String[] titles) {
            super(fm);
            this.mFragmentList = mFragmentList;
            this.titles = titles;
        }

        /**
         * 描述：获取索引位置的Fragment.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            Log.i(TAG,"LRL position is "+ position);
            Fragment fragment = null;
            if (position < mFragmentList.size()){
                fragment = mFragmentList.get(position);

            }else{
                fragment = mFragmentList.get(0);

            }
            return fragment;
        }

        /**
         * 返回viewpager对应的title。
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Log.i(TAG,"LRL position2    is "+ position);
            if (titles != null && titles.length>0){
                return titles[position];
            }
            return null;
        }

        /**
         * 描述：获取数量.
         * @return
         */
        @Override
        public int getCount() {
            Log.i(TAG,"LRL mFragmentList.size()"+mFragmentList.size());
            return mFragmentList.size();
        }
}
