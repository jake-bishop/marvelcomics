package com.geekmode.marvelcomics;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://gateway.marvel.com";

    private static final String API_PUBLIC_KEY = "fb87f1900046c62163db5378c962bdf9";
    private static final String API_PRIVATE_KEY = "2bbc173e99340db09bee85ba39c0a9471af79e29";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new ApiInterceptor());

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        final Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static class ApiInterceptor implements Interceptor {

        @Override
        public Response intercept(final Chain chain) throws IOException {
            final Request original = chain.request();
            final HttpUrl originalHttpUrl = original.url();

            final String timeStamp = String.valueOf(System.currentTimeMillis());

            final String hashInput = timeStamp + API_PRIVATE_KEY + API_PUBLIC_KEY;
            final String hash = new String(Hex.encodeHex(DigestUtils.md5(hashInput)));
            final HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", API_PUBLIC_KEY)
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("hash", hash)
                    .build();

            final Request.Builder requestBuilder = original.newBuilder().url(url);

            final Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }


}
