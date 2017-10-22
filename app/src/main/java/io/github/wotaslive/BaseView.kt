package io.github.wotaslive

/**
 * Created by Tony on 2017/10/22 13:50.
 * Class description:
 */

interface BaseView<in T> {
    fun setPresenter(presenter: T)
}
