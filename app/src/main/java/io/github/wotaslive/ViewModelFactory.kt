package io.github.wotaslive

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.livelist.LiveListViewModel
import io.github.wotaslive.main.MainViewModel
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
                            ?: ViewModelFactory(application, AppRepository.getInstance()).also { INSTANCE = it }
                }
    }
}