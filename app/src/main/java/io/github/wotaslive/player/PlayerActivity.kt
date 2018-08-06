package io.github.wotaslive.player

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActPlayerBinding
import io.github.wotaslive.widget.MediaController
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class PlayerActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActPlayerBinding
    private var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_player)
        initView()
    }

    private fun initView() {
        with(viewDataBinding) {
            player.isBackgroundPlayEnabled = true
            player.setMediaController(MediaController(this@PlayerActivity))
            player.setFullScreen(this@PlayerActivity)
            player.setVideoPath(intent.getStringExtra(Constants.URL))
            player.start()
//            isLive = intent.getBooleanExtra(Constants.IS_LIVE, false)
        }
    }

    override fun onBackPressed() {
        backPressed = true
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        viewDataBinding.player.suspend()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.player.resume()
    }

    override fun onStop() {
        super.onStop()
        with(viewDataBinding.player) {
            if (backPressed || !isBackgroundPlayEnabled) {
                stopPlayback()
                release(true)
                stopBackgroundPlay()
            } else {
                enterBackground()
            }
        }
        IjkMediaPlayer.native_profileEnd()
    }

    companion object {

        fun startPlayerActivity(context: Context?, path: String, isLive: Boolean) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(Constants.URL, path)
            intent.putExtra(Constants.IS_LIVE, isLive)
            context?.startActivity(intent)
        }
    }
}
