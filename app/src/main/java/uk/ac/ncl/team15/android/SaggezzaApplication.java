package uk.ac.ncl.team15.android;

import android.app.Application;

import uk.ac.ncl.team15.android.retrofit.SaggezzaService;
import uk.ac.ncl.team15.android.util.ValueContainer;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaggezzaApplication extends Application
{
    private static ValueContainer<String> userAuthToken = new ValueContainer<>();
    private static SaggezzaService retrofitService;

    @Override
    public void onCreate() {
        super.onCreate();

        // Intercept HTTP requests to add the 'Authorization' header
        Interceptor interceptor = new Interceptor() { // Leave as anon-inner class for java compatibility <1.8
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                if (userAuthToken.get() != null)
                {
                    //System.out.println("[#] Interceptor: Injecting header with token=" + token.get());
                    Request newRequest = chain.request().newBuilder().addHeader("Authorization", "Token " + userAuthToken.get()).build();
                    return chain.proceed(newRequest);
                }
                else
                    return chain.proceed(chain.request());
            }
        };

        // Make custom OkHttpClient instance which uses our interceptor
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s://%s:%d/", BuildConfig.SERVER_PROTO, BuildConfig.SERVER_HOST, BuildConfig.SERVER_PORT))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.retrofitService = retrofit.create(SaggezzaService.class);
    }

    public static SaggezzaService getRetrofitService()
    {
        return retrofitService;
    }

    public static String getUserAuthToken() {
        return userAuthToken.get();
    }

    public static void setUserAuthToken(String token) {
        userAuthToken.set(token);
    }
}
