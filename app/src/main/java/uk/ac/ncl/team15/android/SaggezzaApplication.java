package uk.ac.ncl.team15.android;

import android.app.Application;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.retrofit.SaggezzaService;
import uk.ac.ncl.team15.android.retrofit.models.ModelAuth;
import uk.ac.ncl.team15.android.retrofit.models.ModelJob;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.BitmapCache;
import uk.ac.ncl.team15.android.util.ValueContainer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.function.Consumer;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaggezzaApplication extends Application
{
    private static SaggezzaApplication instance;

    private boolean initComplete = false;
    private ValueContainer<String> userAuthToken = new ValueContainer<>();
    private ValueContainer<ModelUser> userAuthData = new ValueContainer<>();
    private SaggezzaService retrofitService;

    private static final String BASE_URL = String.format("%s://%s:%d/",
                                                BuildConfig.SERVER_PROTO,
                                                BuildConfig.SERVER_HOST,
                                                BuildConfig.SERVER_PORT);

    @Override
    public void onCreate() {
        super.onCreate();

        SaggezzaApplication.instance = this;

        // Intercept HTTP requests to add the 'Authorization' header
        Interceptor interceptor = new Interceptor() { // Leave as anon-inner class for java compatibility <1.8
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                if (userAuthToken.get() != null)
                {
                    //Log.v("SaggezzaApplication", "Injecting header with token=" + userAuthToken.get());
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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.retrofitService = retrofit.create(SaggezzaService.class);

        File appFileDir = getFilesDir();
        File authTokenFile = new File(appFileDir, ".token");
        if (authTokenFile.exists()) {
            Log.v("SaggezzaApplication", "authTokenFile exists, attempting to read token");
            byte[] tokenData = new byte[(int) authTokenFile.length()];
            try (InputStream is = openFileInput(".token")) {
                DataInputStream dis = new DataInputStream(is);
                dis.readFully(tokenData);
            } catch (IOException ioe) {
                Log.e("SaggezzaApplication", "error reading token file", ioe);
            }
            String token = new String(tokenData, Charset.forName("UTF-8"));
            userAuthToken.set(token);
            Call<ModelUser> callMu = retrofitService.self();
            callMu.enqueue(new Callback<ModelUser>() {
                @Override
                public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                    if (response.code() == 200) {
                        SaggezzaApplication.getInstance().setUserAuthData(response.body());
                        Log.v("SaggezzaApplication", "successfully authenticated using fs token");
                    }
                    else {
                        try {
                            authTokenFile.delete();
                        } catch (Throwable t) {
                            Log.e("SaggezzaApplication", "unable to delete invalid auth token", t);
                        }
                    }
                    initComplete = true;
                }

                @Override
                public void onFailure(Call<ModelUser> call, Throwable throwable) {
                    Log.e("SaggezzaApplication", "retrofit service failure", throwable);
                    initComplete = true;
                }
            });
        } else {
            initComplete = true;
        }
    }

    public void getUserDataById(int userId, Consumer<ModelUser> callback) {
        Call<ModelUser> callMu = retrofitService.users(userId);
        callMu.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable throwable) {
                if (throwable != null)
                    Log.e("RETROFIT", "getUserDataById("+userId+")", throwable);
                callback.accept(null);
            }
        });
    }

    public void getJobDataById(int jobId, Consumer<ModelJob> callback) {
        Call<ModelJob> callMu = retrofitService.jobs(jobId);
        callMu.enqueue(new Callback<ModelJob>() {
            @Override
            public void onResponse(Call<ModelJob> call, Response<ModelJob> response) {
                callback.accept(response.body());
            }

            @Override
            public void onFailure(Call<ModelJob> call, Throwable throwable) {
                if (throwable != null)
                    Log.e("RETROFIT", "getJobDataById("+jobId+")", throwable);
                callback.accept(null);
            }
        });
    }

    private void writeAuthTokenToFs(String token) {
        try {
            File appFileDir = getFilesDir();
            File authTokenFile = new File(appFileDir, ".token");
            if (authTokenFile.exists())
                authTokenFile.delete();
            if (token == null)
                return;
            try (OutputStream os = openFileOutput(".token", MODE_PRIVATE)) {
                os.write(token.getBytes(Charset.forName("UTF-8")));
            }
        } catch (IOException ioe) {
            Log.e("SaggezzaApplication", "error writing auth token to fs", ioe);
        }
    }

    public boolean hasInit() {
        return initComplete;
    }

    public SaggezzaService getRetrofitService() {
        return retrofitService;
    }

    public String getUserAuthToken() {
        return userAuthToken.get();
    }

    public void setUserAuthToken(String token) {
        userAuthToken.set(token);

        writeAuthTokenToFs(token);
    }

    public ModelUser getUserAuthData() {
        return userAuthData.get();
    }

    public void setUserAuthData(ModelUser token) {
        userAuthData.set(token);
    }

    public static SaggezzaApplication getInstance() {
        return SaggezzaApplication.instance;
    }

    public static String userImageUrl(ModelUser modelUser) {
        return BASE_URL + String.format("users/%d/img", modelUser.getId());
    }
}
