package io.github.wotaslive.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.wotaslive.R
import io.github.wotaslive.dynamiclist.DynamicListFragment
import io.github.wotaslive.livelist.LiveListFragment
import io.github.wotaslive.roomlist.RoomListFragment
import io.github.wotaslive.showlist.ShowListFragment
import java.util.*

/**
 * Created by Tony on 2017/10/16 15:27.
 * Class description:
 */

class MainPagerAdapter internal constructor(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragments: MutableList<Fragment>
    private val mTitles: MutableList<String>

    init {
        mFragments = ArrayList()
        mFragments.add(LiveListFragment.newInstance())
        mFragments.add(ShowListFragment.newInstance())
        mFragments.add(RoomListFragment.newInstance())
        mFragments.add(DynamicListFragment.newInstance())
        mTitles = ArrayList()
        mTitles.add(context.getString(R.string.tab_stream))
        mTitles.add(context.getString(R.string.tab_show))
        mTitles.add(context.getString(R.string.tab_room))
        mTitles.add(context.getString(R.string.tab_dynamic))
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
