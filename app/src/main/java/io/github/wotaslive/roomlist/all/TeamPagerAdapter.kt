package io.github.wotaslive.roomlist.all

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.wotaslive.roomlist.RoomListFragment

class TeamPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val titles = ArrayList<String>()
    private val frags = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return frags[position]
    }

    override fun getCount(): Int {
        return frags.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun updateTitles(list: List<String>?) {
        titles.clear()
        titles.addAll(list.orEmpty())
    }

    fun updatePage(list: List<List<Int>>?) {
        frags.clear()
        list.orEmpty().forEach {
            frags.add(RoomListFragment.newInstance(it))
        }
    }
}