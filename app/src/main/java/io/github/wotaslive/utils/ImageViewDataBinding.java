package io.github.wotaslive.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.orhanobut.logger.Logger;

import io.github.wotaslive.GlideApp;

public class ImageViewDataBinding {
	@BindingAdapter("imageUrl")
	public static void setImageUrl(ImageView imageView, String url) {
		GlideApp.with(imageView.getContext())
				.load(url)
				.override(Target.SIZE_ORIGINAL)
				.into(imageView);
	}
}
