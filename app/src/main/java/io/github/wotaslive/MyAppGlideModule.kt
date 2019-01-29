package io.github.wotaslive

import android.content.Context

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by codeczx on 2017/10/12.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val cacheSize = 1024 * 1024 * 200
        builder.setMemoryCache(LruResourceCache(cacheSize.toLong()))
    }
}
