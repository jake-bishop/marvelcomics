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

            String publicApiKey = null;
            String privateApiKey = null;

            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("gradle.properties"));
                publicApiKey = properties.getProperty("com.geekmode.marvel-public-api-key");
                privateApiKey = properties.getProperty("com.geekmode.marvel-private-api-key");
            } catch (IOException e) {
                Log.e(TAG, "Error: unable to load properties!");
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
