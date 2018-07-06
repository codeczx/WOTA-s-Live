package io.github.wotaslive.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import io.github.wotaslive.GlideApp;
import io.github.wotaslive.data.AppRepository;

public class ImageViewDataBinding {
	@BindingAdapter("imageUrl")
	public static void setImageUrl(ImageView imageView, String url) {
		GlideApp.with(imageView.getContext())
				.load(AppRepository.IMG_BASE_URL + url)
				.override(Target.SIZE_ORIGINAL)
				.into(imageView);
	}
}
