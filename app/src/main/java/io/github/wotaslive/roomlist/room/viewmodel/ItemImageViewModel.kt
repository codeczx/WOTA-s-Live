package io.github.wotaslive.roomlist.room.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.google.gson.JsonParser
import io.github.wotaslive.data.model.ExtInfo

class ItemImageViewModel(obj: Any) : ViewModel() {
    private val extInfo: ExtInfo by lazy {
        obj as ExtInfo
    }
    private val imageUrl: String by lazy {
        JsonParser().parse(extInfo.bodys).asJsonObject["url"].asString
    }
    val avatar = ObservableField<String>(extInfo.senderAvatar)
    val senderName = ObservableField<String>(extInfo.senderName)
    val senderLevel = ObservableField<String>(extInfo.senderLevel)
    val image = ObservableField<String>(imageUrl)
}