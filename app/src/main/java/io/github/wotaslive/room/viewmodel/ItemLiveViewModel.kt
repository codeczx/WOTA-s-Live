package io.github.wotaslive.room.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.ExtInfo

class ItemLiveViewModel(obj: Any) : ViewModel() {
    private val extInfo: ExtInfo by lazy {
        obj as ExtInfo
    }
    val avatar = ObservableField<String>(extInfo.senderAvatar)
    val senderName = ObservableField<String>(extInfo.senderName)
    val senderLevel = ObservableField<String>(extInfo.senderLevel)
    val liveCover = ObservableField<String>(extInfo.referencecoverImage)
    val liveContent = ObservableField<String>(extInfo.referenceContent)
    val liveTitle = ObservableField<String>(extInfo.referenceTitle)
    val liveId = ObservableField<String>(extInfo.referenceObjectId)
}