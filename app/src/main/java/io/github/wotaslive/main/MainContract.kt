package io.github.wotaslive.main

import android.content.Context
import com.github.florent37.materialviewpager.MaterialViewPager
import io.github.wotaslive.BasePresenter
import io.github.wotaslive.BaseView

/**
 * Created by Tony on 2017/10/29 1:16.
 * Class description:
 */
interface MainContract {
    interface MainView : BaseView<MainPresenter> {
        fun updateHeader(listener: MaterialViewPager.Listener)
    }

    interface MainPresenter : BasePresenter, MaterialViewPager.Listener {
        fun loanHeader(context: Context)
    }
}