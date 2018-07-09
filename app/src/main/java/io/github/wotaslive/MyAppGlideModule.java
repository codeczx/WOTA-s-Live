package io.github.wotaslive;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by codeczx on 2017/10/12.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		super.applyOptions(context, builder);
		int cacheSize = 1024 * 1024 * 200;
		builder.setMemoryCache(new LruResourceCache(cacheSize));
	}
}
