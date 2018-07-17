package io.github.wotaslive.livelist

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo

class LiveItemViewModel(context: Context, val room: LiveInfo.ContentBean.RoomBean) : ViewModel() {
    private val statusString by lazy {
        context.getString(
                if (room.liveType == 1)
                    R.string.live
                else
                    R.string.review
        )
    }
    private val timeString by lazy {
        context.getString(R.string.live_start_time,
                room.startTime)
    }
    private val urlString by lazy {
        var imgUrl = room.picPath
        imgUrl?.let {
            if (it.indexOf(',') != -1) {
                imgUrl = it.substring(0, it.indexOf(','))
            }
        }
        imgUrl
    }

    val status = ObservableField<String>(statusString)
    val title = ObservableField<String>(room.title)
    val subTitle = ObservableField<String>(room.subTitle)
    val time = ObservableField<String>(timeString)
    val url = ObservableField<String>(urlString)
}