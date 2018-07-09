package io.github.wotaslive.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import io.github.wotaslive.GlideApp;
import io.github.wotaslive.data.AppRepository;

public class ImageViewDataBinding {
	@BindingAdapter("imageUrl")
	public static void setImageUrl(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) return;
		GlideApp.with(imageView.getContext())
				.load(AppRepository.IMG_BASE_URL + url)
				.override(Target.SIZE_ORIGINAL)
				.into(imageView);
	}

	@BindingAdapter("avatarUrl")
	public static void setAvatarUrl(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) return;
		if (url.startsWith("http")) {
			if (url.contains("ram")) {
				url = url.substring(0, url.indexOf("?"));
			}
		}
		else {
			url = AppRepository.IMG_BASE_URL + url;
		}
		GlideApp.with(imageView.getContext())
				.load(url)
				.override(Target.SIZE_ORIGINAL)
				.into(imageView);
	}
}
