package io.github.wotaslive.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codeczx on 2017/10/9.
 */

public class AppRepository {

    private volatile static AppRepository mInstance;
    private ApiServices snhApi;

    private AppRepository() {
    }

    public static AppRepository getInstance() {
        if (mInstance == null) {
            synchronized (AppRepository.class) {
                if (mInstance == null) {
                    mInstance = new AppRepository();
                }
            }
        }
        return mInstance;
    }

    public ApiServices getSNHApi() {
        if (mInstance.snhApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(ApiServices.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mInstance.snhApi = retrofit.create(ApiServices.class);
        }
        return mInstance.snhApi;
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
    }
}
