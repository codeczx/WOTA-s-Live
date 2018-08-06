package io.github.wotaslive.dynamiclist

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.utils.ChatDateTime

class DynamicItemViewModel(context: Context, callback: io.github.wotaslive.roomlist.room.pictures.DynamicPicturesAdapter.Callback,
                           data: DynamicInfo.Content.Data, member: SyncInfo.Content.MemberInfo?) : ViewModel() {
    val avatarUrl = ObservableField<String>(member?.avatar)
    val name = ObservableField<String>(member?.real_name)
    val time = ObservableField<String>(ChatDateTime.getNiceTime(data.ctime))
    val content = ObservableField<String>(data.content)
    val adapter = ObservableField<DynamicPicturesAdapter>(data.picture?.let {
        DynamicPicturesAdapter(callback, it)
    })
    val layoutManager = ObservableField<RecyclerView.LayoutManager>(
            data.picture?.let {
                return@let when {
                    it.size == 1 -> LinearLayoutManager(context)
                    it.size < 5 -> GridLayoutManager(context, 2)
                    else -> GridLayoutManager(context, 3)
                }
            })
    val pictures = data.picture
}