package io.github.wotaslive.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;

import io.github.wotaslive.GlideApp;
import io.github.wotaslive.R;
import io.github.wotaslive.data.AppRepository;

public class ImageViewDataBinding {
	private static String checkUrl(String url) {
		if (url.startsWith("http")) {
			// 网易NOS的证书Glide不认可，替换成http
			if (url.startsWith("https://nos.netease.com")) {
				url = url.replace("https", "http");
			}
			if (url.contains("?")) {
				url = url.substring(0, url.indexOf("?"));
			}
		}
		else {
			url = AppRepository.IMG_BASE_URL + url;
		}
		return url;
	}

	@BindingAdapter("imageUrl")
	public static void setImageUrl(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) return;
		if (url.startsWith("#")) {
			GlideApp.with(imageView.getContext())
					.load(new ColorDrawable(Color.parseColor(url)))
					.into(imageView);
		}
		else {
			GlideApp.with(imageView.getContext())
					.load(checkUrl(url))
					.override(Target.SIZE_ORIGINAL)
					.dontAnimate()
					.into(imageView);
		}
	}

	@BindingAdapter("roundImageUrl")
	public static void setRoundImageUrl(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) return;
		int size = imageView.getResources().getDimensionPixelSize(R.dimen.room_max_img_width);
		GlideApp.with(imageView.getContext())
				.load(checkUrl(url))
				.override(size, size)
				.transform(new RoundedCorners(imageView.getResources().getDimensionPixelOffset(R.dimen.room_radius)))
				.into(imageView);
	}

	@BindingAdapter("avatarUrl")
	public static void setAvatarUrl(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) return;
		GlideApp.with(imageView.getContext())
				.load(checkUrl(url))
				.into(imageView);
	}
}
