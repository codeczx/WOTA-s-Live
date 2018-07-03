package io.github.wotaslive.showlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.target.Target
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ShowInfo
import kotlinx.android.synthetic.main.item_show.view.*
import java.util.*

/**
 * Created by Tony on 2017/10/22 21:10.
 * Class description:
 */
class ShowListAdapter(callbacks: Callbacks) : RecyclerView.Adapter<ShowListAdapter.ShowViewHolder>() {
    private var mList: ArrayList<ShowInfo.ContentBean.ShowBean> = ArrayList()
    private var mCallbacks: Callbacks = callbacks

    interface Callbacks {
        fun onCoverClick(show: ShowInfo.ContentBean.ShowBean)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder.newInstance(parent, mCallbacks)
    }

    fun updateShowList(list: List<ShowInfo.ContentBean.ShowBean>?) {
        mList.clear()
        if (list != null) {
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    class ShowViewHolder private constructor(itemView: View, callbacks: Callbacks) : RecyclerView.ViewHolder(itemView) {
        private val mCallbacks: Callbacks = callbacks

        companion object {
            fun newInstance(viewGroup: ViewGroup?, callbacks: Callbacks): ShowViewHolder {
                val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.item_show, viewGroup, false)
                return ShowViewHolder(view, callbacks)
            }
        }

        fun bind(show: ShowInfo.ContentBean.ShowBean) {
            itemView.tv_title.text = show.title
            itemView.tv_subtitle.text = show.subTitle
            itemView.tv_status.setText(if (show.isIsOpen) R.string.show_status_Streaming else R.string.show_status_future)
            itemView.tv_time.text = String.format(Locale.US,
                    itemView.resources.getString(R.string.live_start_time), show.startTime)
            var path = show.picPath
            if (path.contains(",")) {
                val paths = path.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                path = paths[0]
            }
            GlideApp.with(itemView.context)
                    .load(AppRepository.IMG_BASE_URL + path)
                    .override(Target.SIZE_ORIGINAL)
                    .into(itemView.iv_cover)
            itemView.iv_cover.setOnClickListener {
                mCallbacks.onCoverClick(show)
            }
        }
    }
}