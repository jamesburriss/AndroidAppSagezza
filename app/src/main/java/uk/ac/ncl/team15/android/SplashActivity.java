package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.VideoView;


public class SplashActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);



        videoView = (VideoView)findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoView.setZOrderOnTop(true);//this line solve the problem
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(mp -> {
            if(isFinishing())
                return;

            new Thread() {
                @Override
                public void run() {
                    try {
                        while (!SaggezzaApplication.getInstance().hasInit()) {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException ie) {
                        Log.e("SplashActivity", "interrupted while waiting for app init", ie);
                    }

                    Class<?> activityTarget =
                            SaggezzaApplication.getInstance().getUserAuthData() == null ?
                                    LoginActivity.class : DashboardActivity.class;
                    startActivity(new Intent(SplashActivity.this, activityTarget));
                    finish();
                }
            }.start();
        });
        videoView.start();
    }
}
