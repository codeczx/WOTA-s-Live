package io.github.wotaslive.dynamiclist

import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
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

class DynamicAdapter(private val fragment: Fragment) : BaseQuickAdapter<DynamicInfo.Content.Data, BaseViewHolder>(R.layout.item_dynamic) {
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
                    it1.setOnItemClickListener { position, _ ->
                        val array = arrayOfNulls<String>(it.picture.orEmpty().size)
                        val views = arrayOfNulls<View>(it1.childCount)
                        it.picture?.forEachIndexed { index, picture ->
                            array[index] = checkUrl(picture.filePath)
                        }
                        for (i in 0 until it1.childCount) {
                            views[i] = it1.getChildAt(i)
                        }
                        Diooto(fragment.context)
                                .urls(array)
                                .type(DiootoConfig.PHOTO)
                                .position(position)
                                .views(views)
                                .loadPhotoBeforeShowBigImage { sketchImageView, pos ->
                                    sketchImageView.displayImage(checkUrl(fragment.getString(R.string.resize_250) +
                                            it.picture.orEmpty()[pos].filePath))
                                }
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