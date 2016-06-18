package com.example.caitlin.feedbacksave;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoFeedback extends SuperActivity {
    //TextView tvFeedback;
    //StorageReference photoRef;
    String downloadPath;
    String photoRefPath;
    String fileName;
    ImageView ivFeedback;
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

        downloadFile();

//        Context context = this;
//        DropBoxAPIManager.getInstance().downloadFile(context);

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

    public void downloadFile(){
        new DownloadFileAsyncTask(this, (ImageView) findViewById(R.id.ivFB)).execute("");
    }
}
