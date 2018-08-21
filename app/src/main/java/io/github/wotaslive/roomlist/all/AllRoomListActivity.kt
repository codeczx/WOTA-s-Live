package io.github.wotaslive.roomlist.all

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActAllRoomListBinding
import io.github.wotaslive.utils.obtainViewModel

class AllRoomListActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActAllRoomListBinding
    private lateinit var viewModel: AllRoomListViewModel
    private val adapter = TeamPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_all_room_list)
        viewModel = obtainViewModel(AllRoomListViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this)
        }
        viewModel.groups.observe(this, Observer {
            invalidateOptionsMenu()
        })
        viewModel.ids.observe(this, Observer {
            adapter.updatePage(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.titles.observe(this, Observer {

            adapter.updateTitles(it)
        })
        with(viewDataBinding) {
            pager.adapter = adapter
            tab.setupWithViewPager(pager)
        }

        viewModel.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        viewModel.groups.value?.let { it ->
            if (it.isNotEmpty()) {
                it.forEach {
                    menu?.add(Menu.NONE, it.group_id, it.group_id, it.group_name)
                }
                return true
            }
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        viewModel.groups.value?.forEach {
            if (it.group_id == item?.itemId) {
                viewModel.switchToGroup(it.group_id)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startAllRoomListActivity(activity: Activity) {
            val intent = Intent(activity, AllRoomListActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
