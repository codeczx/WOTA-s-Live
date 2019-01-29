package io.github.wotaslive.dynamiclist

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.databinding.ItemDynamicPicBinding
import io.github.wotaslive.roomlist.room.pictures.ItemViewModel
import io.github.wotaslive.utils.ChatDateTime
import io.github.wotaslive.utils.checkUrl
import net.moyokoo.diooto.Diooto
import net.moyokoo.diooto.config.DiootoConfig
import net.moyokoo.diooto.interfaces.CircleIndexIndicator
import net.moyokoo.diooto.interfaces.DefaultPercentProgress

class DynamicItemViewModel(context: Context, recyclerView: RecyclerView,
                           data: DynamicInfo.Content.Data, member: SyncInfo.Content.MemberInfo?) : ViewModel() {
    val avatarUrl = ObservableField<String>(member?.avatar)
    val name = ObservableField<String>(member?.real_name)
    val time = ObservableField<String>(ChatDateTime.getNiceTime(data.ctime))
    val content = ObservableField<String>(data.content)
    val adapter = ObservableField<DynamicPicturesAdapter>(data.picture?.let {
        DynamicPicturesAdapter(it).also {
            it.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val urls = arrayOfNulls<String>(adapter.data.size)
                adapter.data.forEachIndexed { index, any ->
                    urls[index] = checkUrl((any as DynamicInfo.Content.Data.Picture).filePath)
                }
                Diooto(context)
                        .urls(urls)
                        .type(DiootoConfig.PHOTO)
                        .fullscreen(false)
                        .position(position)
                        .views(recyclerView, R.id.iv_image)
                        .setIndicator(CircleIndexIndicator())
                        .setProgress(DefaultPercentProgress())
                        .start()
            }
        }
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

    class DynamicPicturesAdapter(list: List<DynamicInfo.Content.Data.Picture>) : BaseQuickAdapter<DynamicInfo.Content.Data.Picture, BaseViewHolder>(R.layout.item_dynamic_pic, list) {

        override fun convert(helper: BaseViewHolder?, item: DynamicInfo.Content.Data.Picture) {
            helper?.itemView?.let {
                val binding: ItemDynamicPicBinding? = DataBindingUtil.bind(it)
                binding?.viewModel = ItemViewModel(item.filePath)
                it.tag = item
            }
        }
    }
}