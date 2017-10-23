package io.github.wotaslive.showlist

import io.github.wotaslive.BasePresenter
import io.github.wotaslive.BaseView
import io.github.wotaslive.data.model.ShowInfo

/**
 * Created by Tony on 2017/10/22 13:37.
 * Class description:
 */
class ShowListContract {
    interface ShowListView : BaseView<ShowListPresenter> {
        fun refreshUI()
        fun updateShow(list: List<ShowInfo.ContentBean.ShowBean>?)
        fun showMenu()
    }

    interface ShowListPresenter : BasePresenter {
        fun getShowList()
        fun setClipboard(text: String)
    }
}