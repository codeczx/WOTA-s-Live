package io.github.wotaslive.room

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.ActRoomDetailBinding
import io.github.wotaslive.utils.obtainViewModel


class RoomDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: RoomViewModel
    private lateinit var viewDataBinding: ActRoomDetailBinding
    private val adapter = RoomDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(RoomViewModel::class.java)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_room_detail)
        setupBinding()

        val extras = intent.extras
        supportActionBar?.let {
            it.title = extras.getString(Constants.ROOM_CREATOR) + ":" + extras.getString(Constants.ROOM_NAME)
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewModel.roomId = extras.getInt(Constants.ROOM_ID)
        viewModel.url.set(extras.getString(Constants.ROOM_BG_PATH))
        viewModel.roomDetailData.observe(this, Observer {
            adapter.submitList(it)
            viewDataBinding.srlRoomDetail.finishRefresh()
        })
        viewModel.load()
    }

    private fun setupBinding() {
        with(viewDataBinding) {
            viewModel = this@RoomDetailActivity.viewModel
            setLifecycleOwner(this@RoomDetailActivity)
            with(srlRoomDetail) {
                setOnRefreshListener {
                    this@RoomDetailActivity.viewModel.load()
                }
            }
            with(rvRoomDetail) {
                layoutManager = LinearLayoutManager(this@RoomDetailActivity, LinearLayoutManager.VERTICAL, true)
                adapter = this@RoomDetailActivity.adapter
            }
        }
    }

    companion object {
        fun startRoomDetailActivity(context: Context, content: RoomInfo.ContentBean) {
            val intent = Intent(context, RoomDetailActivity::class.java)
            intent.putExtra(Constants.ROOM_ID, content.roomId)
            intent.putExtra(Constants.ROOM_NAME, content.roomName)
            intent.putExtra(Constants.ROOM_CREATOR, content.creatorName)
            intent.putExtra(Constants.ROOM_BG_PATH, content.bgPath)
            context.startActivity(intent)
        }
    }
}