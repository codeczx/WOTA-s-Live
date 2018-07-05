package io.github.wotaslive.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.R;
import io.github.wotaslive.livelist.LiveListFragment;
import io.github.wotaslive.roomlist.RoomListFragment;
import io.github.wotaslive.showlist.ShowListFragment;

/**
 * Created by Tony on 2017/10/16 15:27.
 * Class description:
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;
	private List<String> mTitles;

	MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<>();
		mFragments.add(LiveListFragment.Companion.newInstance());
		mFragments.add(ShowListFragment.Companion.newInstance());
		mFragments.add(new RoomListFragment());
		mTitles = new ArrayList<>();
		mTitles.add(context.getString(R.string.tab_stream));
		mTitles.add(context.getString(R.string.tab_show));
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
