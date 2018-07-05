package io.github.wotaslive.data;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;

import io.github.wotaslive.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

	@Override
	public Response intercept(@NonNull Chain chain) throws IOException {
		Request request = chain.request();
		SPUtils spUtils = SPUtils.getInstance(Constants.SP_NAME);
		request = request.newBuilder()
				.addHeader(Constants.HEADER_KEY_IMEI, PhoneUtils.getIMEI())
				.addHeader(Constants.HEADER_KEY_TOKEN, spUtils.getString(Constants.HEADER_KEY_TOKEN, "0"))
				.addHeader(Constants.HEADER_KEY_OS, Constants.HEADER_VALUE_OS)
				.addHeader(Constants.HEADER_KEY_VERSION, Constants.HEADER_VALUE_VERSION)
				.addHeader(Constants.HEADER_KEY_BUILD, Constants.HEADER_VALUE_BUILD)
				.addHeader(Constants.HEADER_KEY_USER_AGENT, Constants.HEADER_VALUE_USER_AGENT)
				.addHeader(Constants.HEADER_KEY_CONTENT_TYPE, Constants.HEADER_VALUE_CONTENT_TYPE)
				.build();
		return chain.proceed(request);
	}
}
