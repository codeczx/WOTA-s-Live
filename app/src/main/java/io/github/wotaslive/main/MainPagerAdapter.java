package io.github.wotaslive.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.R;
import io.github.wotaslive.list.ListFragment;

/**
 * Created by Tony on 2017/10/16 15:27.
 * Class description:
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;
	private List<String> mTitles;

	public MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<>();
		mFragments.add(new ListFragment());
		mFragments.add(new Fragment());
		mTitles = new ArrayList<>();
		mTitles.add(context.getString(R.string.tab_stream));
		mTitles.add(context.getString(R.string.tab_room));
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}
}
