package com.example.caitlin.feedbacksave;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.DropBoxManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class PhotoFeedback extends SuperActivity {
    //TextView tvFeedback;
    //StorageReference photoRef;
    String downloadPath;
    String photoRefPath;
    String fileName;
    //ImageView ivFeedback;
    Uri uploadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feedback);



        //ivFeedback = (ImageView) findViewById(R.id.ivFB);
        //tvFeedback = (TextView) findViewById(R.id.tvFB);

        Bundle extras = getIntent().getExtras();
        fileName = extras.getString("Filename");
        //photoRef = extras.getParcelable("photoRef");
        //photoRefPath = extras.getString("photoRefPath");
        //uploadUri = extras.get
        //downloadPath = extras.getString("path");
        //Log.d("path extra", downloadPath);

        //downloadFile();

        Context context = this;
        DropBoxAPIManager.getInstance().downloadFile(context);

        //DropboxAPI.Entry();
//        DropboxAPI.Entry existingEntry = null;
//        try {
//            existingEntry = DropBoxAPIManager.getInstance().getDropBoxApi().metadata("/20160601_213528.jpg", 1, null, false, null);
//            Log.d("DB rev is", existingEntry.rev);
//        } catch (DropboxException e) {
//            e.printStackTrace();
//            Log.d("shit failed", "too bad");
//        }
//        Log.i("DbExampleLog", "The file's rev is now: " + existingEntry.rev);

        //String root = DropboxAPI.Entry.root;
//        String outPath = "app_folder/FeedbackSave/" + fileName;
//        File outFile = new File(outPath);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(outFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            mRequest = DropBoxAPIManager.getInstance().getDropBoxApi().putFileOverwriteRequest("/"+fileName, fis, outFile.length(),null);
//        } catch (DropboxException e) {
//            e.printStackTrace();
//        }

        //photoRef = storageRootRefTest.child(photoRefPath);
    }

//    private final Handler handler(){
//        public void handleMessage(Message message){
//            ArrayList<String> result = message.getData().getStringArraylist("");
//
//            for (String fileName : result) {
//
//            }
//        }
//    }

    private void downloadFile(FileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadFileAsyncTask(PhotoFeedback.this, DropBoxClient.getClient(DropBoxAPIManager.getInstance().getToken()), new DownloadFileAsyncTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                dialog.dismiss();

                if (result != null) {
                    viewFileInExternalApp(result);
                }
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.d("Failed to dwnld file.", "rottig");
                Toast.makeText(PhotoFeedback.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(file);

    }

    private void viewFileInExternalApp(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(Uri.fromFile(result), type);

        // Check for a handler first to avoid a crash
        PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            startActivity(intent);
        }
    }


//    private void beginDownload() {
//        // Get path
//        //String path = "photos/" + mFileUri.getLastPathSegment();
//
//        // Kick off download service
//        Intent intent = new Intent(this, MyDownloadService.class);
//        intent.setAction(MyDownloadService.ACTION_DOWNLOAD);
//        intent.putExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH, path);
//        startService(intent);
//
////        // Show loading spinner
////        showProgressDialog();
//    }

    //islandRef = storageRef.child("images/island.jpg");

//    public void downloadPhoto() {
//
//        //File localFile = File.createTempFile("images", "jpg");
//
////        photoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
////            @Override
////            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
////
//        storageRootRefTest.child(downloadPath).getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
//                new ImageDownloadAsyncTask((ImageView) findViewById(R.id.ivFB)).execute(taskSnapshot);
//
//                Toast.makeText(PhotoFeedback.this, "ivfeedback is gezet", Toast.LENGTH_SHORT).show();
//                //Log.d("ivfeedback", ivFeedback.toString());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.w("download:FAILURE", exception);
//                // close stream????
//            }
//        });
//    }
}
