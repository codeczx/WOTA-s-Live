package io.github.wotaslive.player

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_player)
        initView()
    }

    private fun initView() {
        with(viewDataBinding) {
            player.setFullScreen(this@PlayerActivity)
            player.setVideoPath(intent.getStringExtra(Constants.URL))
            player.start()
//            isLive = intent.getBooleanExtra(Constants.IS_LIVE, false)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewDataBinding.player.stopPlayback()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.player.resume()
    }

    override fun onStop() {
        super.onStop()
        viewDataBinding.player.stopPlayback()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.player.release(true)
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
