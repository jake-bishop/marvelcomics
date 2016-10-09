package com.geekmode.marvelcomics.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Properties;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://gateway.marvel.com";

    private static final String TAG = ServiceGenerator.class.getSimpleName();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Gson gson = new GsonBuilder().setLenient().create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    private static Retrofit retrofit = null;
    private static Context applicationContext;

    public static <S> S getService(Class<S> serviceClass, Context applicationContext) {
        ServiceGenerator.applicationContext = applicationContext;

        httpClient.addInterceptor(new ApiInterceptor());

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        if(retrofit == null) {
            retrofit = builder
                    .client(httpClient.build())
                    .build();
        }

        return retrofit.create(serviceClass);
    }

    private static class ApiInterceptor implements Interceptor {

        @Override
        public Response intercept(final Chain chain) throws IOException {
            final Request original = chain.request();
            final HttpUrl originalHttpUrl = original.url();

            final String timeStamp = String.valueOf(System.currentTimeMillis());

            String publicApiKey = null;
            String privateApiKey = null;

            Properties properties = new Properties();
            try {
                properties.load(applicationContext.getAssets().open("auth.properties"));
                publicApiKey = properties.getProperty("marvel.public.key");
                privateApiKey = properties.getProperty("marvel.private.key");
            } catch (IOException e) {
                Log.e(TAG, "Error: unable to load properties!" + e.getMessage());
                return null;
            }

            final String hashInput = timeStamp + privateApiKey + publicApiKey;
            final String hash = new String(Hex.encodeHex(DigestUtils.md5(hashInput)));
            final HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", publicApiKey)
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("hash", hash)
                    .build();

            final Request.Builder requestBuilder = original.newBuilder().url(url);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
