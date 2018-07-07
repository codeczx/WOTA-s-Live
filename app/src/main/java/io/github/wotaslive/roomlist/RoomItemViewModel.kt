package io.github.wotaslive.roomlist

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.RoomInfo

class RoomItemViewModel(val content: RoomInfo.ContentBean) : ViewModel() {
    val name = ObservableField<String>(content.creatorName)
    val comment = ObservableField<String>(content.comment)
    val time = ObservableField<String>(content.commentTime)
    val url = ObservableField<String>(content.roomAvatar)
}