package io.github.wotaslive

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.dynamiclist.DynamicListViewModel
import io.github.wotaslive.livelist.LiveListViewModel
import io.github.wotaslive.login.LoginViewModel
import io.github.wotaslive.main.MainViewModel
import io.github.wotaslive.roomlist.RoomListViewModel
import io.github.wotaslive.roomlist.room.RoomViewModel
import io.github.wotaslive.roomlist.room.pictures.DynamicPicturesViewModel
import io.github.wotaslive.showlist.ShowListViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
        private val application: Application,
        private val appRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(application, appRepository)
                    isAssignableFrom(LiveListViewModel::class.java) ->
                        LiveListViewModel(application, appRepository)
                    isAssignableFrom(ShowListViewModel::class.java) ->
                        ShowListViewModel(application, appRepository)
                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(application, appRepository)
                    isAssignableFrom(RoomListViewModel::class.java) ->
                        RoomListViewModel(application, appRepository)
                    isAssignableFrom(RoomViewModel::class.java) ->
                        RoomViewModel(application, appRepository)
                    isAssignableFrom(DynamicPicturesViewModel::class.java) ->
                        DynamicPicturesViewModel(application, appRepository)
                    isAssignableFrom(DynamicListViewModel::class.java) ->
                        DynamicListViewModel(application, appRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class:${modelClass.name}")
                }
            } as T

    companion object {
        @SuppressWarnings("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE
                ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE
                            ?: ViewModelFactory(application, AppRepository.instance).also { INSTANCE = it }
                }
    }
}