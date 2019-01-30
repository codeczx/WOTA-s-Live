package io.github.wotaslive.dynamiclist

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.utils.ChatDateTime

class DynamicItemViewModel(data: DynamicInfo.Content.Data, member: SyncInfo.Content.MemberInfo?) : ViewModel() {
    val avatarUrl = ObservableField<String>(member?.avatar)
    val name = ObservableField<String>(member?.real_name)
    val time = ObservableField<String>(ChatDateTime.getNiceTime(data.ctime))
    val content = ObservableField<String>(data.content)
}