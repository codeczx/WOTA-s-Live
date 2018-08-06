package io.github.wotaslive.photo

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActPhotoBinding
import io.github.wotaslive.utils.obtainViewModel

class PhotoActivity : AppCompatActivity() {
    private lateinit var viewModel: PhotoViewModel
    private lateinit var viewDataBinding: ActPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_photo)
        viewModel = obtainViewModel(PhotoViewModel::class.java).also {
            viewDataBinding.setLifecycleOwner(this)
            viewDataBinding.viewModel = it
        }
        viewModel.url.set(intent.extras.getString(Constants.URL))
    }

    fun onImageLongClick(): Boolean {
        Snackbar.make(viewDataBinding.photo, R.string.pic_save, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok) {
                    viewModel.saveToLocal(this@PhotoActivity)
                }
                .show()
        return true
    }

    companion object {
        fun startPhotoActivity(activity: Activity, url: String) {
            val intent = Intent(activity, PhotoActivity::class.java)
            intent.putExtra(Constants.URL, url)
            activity.startActivity(intent)
        }
    }
}
