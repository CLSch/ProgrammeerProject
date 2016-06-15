package com.example.caitlin.feedbacksave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Caitlin on 14-06-16.
 */
public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap bmImage2;

    /** Constructor */
    public ImageDownloadAsyncTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }


    /** Convert an image url to a bitmap image. */
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];

        Log.d("download succes", "ja");
        Bitmap bmImage2 = null;
        try {
            // doe iets
            Log.d("check", "alles");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        //close stream????
        // Local temp file has been created
        return bmImage2;
    }

    public void resizeBitmap(InputStream stream1, InputStream stream2) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Log.d("hier", "gekomen");
        BitmapFactory.decodeStream(stream1, null, options);
        Log.d("bitmapfactory", "gelukt");
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        Log.d("Tot nu toe", "overleeft");
        //stream2.reset();
        //String imageType = options.outMimeType;
        if(imageWidth > imageHeight) {
            options.inSampleSize = calculateInSampleSize(options,512,256);//if landscape
        } else{
            options.inSampleSize = calculateInSampleSize(options,256,512);//if portrait
        }
        Log.d("if else", "overleeft");
        options.inJustDecodeBounds = false;
        Log.d("voor", "factory");
        bmImage2 = BitmapFactory.decodeStream(stream2, null, options);
        Log.d("bmImage2", bmImage2.toString());
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }



//        String imageUrl = urls[0];
//        Bitmap bmImage = null;
//        // get the stream data from the image url
//        try {
//            InputStream inputStream = new java.net.URL(imageUrl).openStream();
//
//            // decode the stream to a bitmap image datatype
//            bmImage = BitmapFactory.decodeStream(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmImage;

    /** Returns the bitmap data. */
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}