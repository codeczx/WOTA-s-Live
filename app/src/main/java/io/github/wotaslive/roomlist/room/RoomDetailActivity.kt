package io.github.wotaslive.roomlist.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.replaceFragmentInActivity


class RoomDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_room_detail)
        viewModel = obtainViewModel(RoomViewModel::class.java)
        replaceFragmentInActivity(RoomDetailFragment.newInstance(), R.id.container)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun startRoomDetailActivity(context: Context, content: RoomInfo.ContentBean) {
            val intent = Intent(context, RoomDetailActivity::class.java)
            intent.putExtra(Constants.MEMBER_ID, content.creatorId)
            intent.putExtra(Constants.ROOM_ID, content.roomId)
            intent.putExtra(Constants.ROOM_NAME, content.roomName)
            intent.putExtra(Constants.ROOM_CREATOR, content.creatorName)
            intent.putExtra(Constants.ROOM_BG_PATH, content.bgPath)
            context.startActivity(intent)
        }
    }
}