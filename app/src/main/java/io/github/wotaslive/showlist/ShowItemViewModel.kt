package io.github.wotaslive.showlist

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo

class ShowItemViewModel(context: Context, val show: ShowInfo.ContentBean.ShowBean) : ViewModel() {
    private val statusString by lazy {
        context.getString(
                if (show.isIsOpen)
                    R.string.show_status_Streaming
                else
                    R.string.show_status_future
        )
    }
    private val timeString by lazy {
        context.getString(R.string.live_start_time,
                show.startTime)
    }
    private val urlString by lazy {
        var imgUrl = show.picPath
        imgUrl?.let {
            if (it.indexOf(',') != -1) {
                imgUrl = it.substring(0, it.indexOf(','))
            }
        }
        imgUrl
    }

    val status = ObservableField<String>(statusString)
    val title = ObservableField<String>(show.title)
    val subTitle = ObservableField<String>(show.subTitle)
    val time = ObservableField<String>(timeString)
    val url = ObservableField<String>(urlString)
}