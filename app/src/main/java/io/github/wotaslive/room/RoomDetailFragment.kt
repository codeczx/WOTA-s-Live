package io.github.wotaslive.room

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.databinding.FragRoomDetailBinding
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar

class RoomDetailFragment : Fragment(), RoomDetailAdapter.Callback {
    private lateinit var viewModel: RoomViewModel
    private lateinit var viewDataBinding: FragRoomDetailBinding
    private val adapter = RoomDetailAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragRoomDetailBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@RoomDetailFragment)
        }
        (activity as RoomDetailActivity).let {
            val extras = it.intent.extras
            viewModel.roomId = extras.getInt(Constants.ROOM_ID)
            viewModel.url.set(extras.getString(Constants.ROOM_BG_PATH))
            viewModel.roomDetailData.observe(this, Observer {
                adapter.submitList(it)
                viewDataBinding.srlRoomDetail.finishRefresh()
            })
            it.setupActionBar(R.id.toolbar) {
                title = extras.getString(Constants.ROOM_CREATOR) + ":" + extras.getString(Constants.ROOM_NAME)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
        setupBinding()
    }

    private fun setupBinding() {
        with(viewDataBinding) {
            viewModel = this@RoomDetailFragment.viewModel
            setLifecycleOwner(this@RoomDetailFragment)
            with(srlRoomDetail) {
                setOnRefreshListener {
                    this@RoomDetailFragment.viewModel.load()
                }
            }
            with(rvRoomDetail) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
                adapter = this@RoomDetailFragment.adapter
            }
        }
    }

    override fun onImageClick(url: String) {
        viewModel.imageUrl.set(url)
        fragmentManager?.run {
            beginTransaction()
                    .add(R.id.container, PhotoFragment.newInstance())
                    .hide(this@RoomDetailFragment)
                    .addToBackStack(this@RoomDetailFragment.javaClass.name)
                    .commit()
        }
    }

    override fun onLiveClick(id: String) {
        Logger.d(id)
    }

    companion object {
        fun newInstance() = RoomDetailFragment()
    }
}
