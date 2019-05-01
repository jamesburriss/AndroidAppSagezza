package uk.ac.ncl.team15.android.util;

/**
 * @Purpose: Misc utility class
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

// based on samples from: https://stackoverflow.com/questions/5776851/load-image-from-url
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView ivTarget;

    public DownloadImageTask(ImageView ivTarget) {
        this.ivTarget = ivTarget;
    }

    protected Bitmap doInBackground(String... urls) {
        String imgUrl = urls[0];
        Bitmap bmImg = null;
        try {
            InputStream in = new java.net.URL(imgUrl).openStream();
            bmImg = BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException fnfe) {
            // fail quietly
        } catch (Exception e) {
            Log.e("DownloadImageTask", e.getMessage());
            e.printStackTrace();
        }
        return bmImg;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null)
            this.ivTarget.setImageBitmap(result);
    }
}
