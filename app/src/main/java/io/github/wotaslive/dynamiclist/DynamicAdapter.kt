package io.github.wotaslive.dynamiclist

import android.databinding.DataBindingUtil
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.liyi.grid.adapter.BaseAutoGridHolder
import com.liyi.grid.adapter.SimpleAutoGridAdapter
import io.github.wotaslive.R
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.databinding.ItemDynamicBinding
import io.github.wotaslive.utils.checkUrl
import io.github.wotaslive.utils.loadThumb
import net.moyokoo.diooto.Diooto
import net.moyokoo.diooto.config.DiootoConfig
import net.moyokoo.diooto.interfaces.DefaultPercentProgress

class DynamicAdapter : BaseQuickAdapter<DynamicInfo.Content.Data, BaseViewHolder>(R.layout.item_dynamic) {
    private val memberMap = HashMap<Int, SyncInfo.Content.MemberInfo>()

    override fun convert(helper: BaseViewHolder?, item: DynamicInfo.Content.Data?) {
        helper?.itemView?.let {
            val binding: ItemDynamicBinding? = DataBindingUtil.bind(helper.itemView)
            item?.let {
                binding?.viewModel = DynamicItemViewModel(item, memberMap[item.memberId])
                binding?.autoGrid?.let { it1 ->
                    it1.setAdapter(SimpleAutoGridAdapter<DynamicInfo.Content.Data.Picture, BaseAutoGridHolder>(item.picture).also { it2 ->
                        it2.setImageLoader { _, item, imageView ->
                            imageView.loadThumb((item as DynamicInfo.Content.Data.Picture).filePath)
                        }
                    })
                    val childViews = arrayOfNulls<View>(it1.childCount)
                    for (i in 0 until childViews.size) {
                        childViews[i] = it1.getChildAt(i)
                    }
                    val urls = arrayOfNulls<String>(it.picture.orEmpty().size)
                    for (i in 0 until urls.size) {
                        urls[i] = checkUrl(it.picture.orEmpty()[i].filePath)
                    }
                    it1.setOnItemClickListener { position, view ->
                        Diooto(view.context)
                                .urls(urls)
                                .type(DiootoConfig.PHOTO)
                                .position(position)
                                .fullscreen(false)
                                .views(childViews)
                                .setProgress(DefaultPercentProgress())
                                .start()
                    }
                }
            }
        }
    }

    fun updateMember(members: Map<Int, SyncInfo.Content.MemberInfo>?) {
        members?.let {
            memberMap.putAll(it)
        }
    }
}