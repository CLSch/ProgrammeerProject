/**
 * AddPhotoFeedbackActivity.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

// https://www.sitepoint.com/adding-the-dropbox-api-to-an-android-app/

/**
 * This activity gets called when a user chooses a picture from the gallery.
 */
public class AddPhotoFeedbackActivity extends SuperActivity {
    EditText etFBName;
    TextView errorMes;
    String FBName;
    DBHelper helper;
    String subject;
    //boolean checkPerm;
    private static int RESULT_GALLERY = 1;
    private static int IMAGE_REQUEST_CODE = 1;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_feedback);

        helper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        subject = extras.getString("subjectName");

        token = DropBoxAPIManager.getInstance().getToken();

        etFBName = (EditText) findViewById(R.id.etFBName);
        errorMes = (TextView) findViewById(R.id.tvErrorPFB);

        assert errorMes != null;
        errorMes.setTextColor(Color.RED);

//       checkPerm = Utility.checkPermission(AddPhotoFeedbackActivity.this);
    }

    /** When the user chooses a picture from the gallery this method gets called. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        // Check which request we're responding to
        if (requestCode == IMAGE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Image URI received
                File file = new File(URI_to_Path.getPath(getApplication(), data.getData()));
                if (file != null) {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 2;
//                    Bitmap bitmap = BitmapFactory.decodeFile(URI_to_Path.getPath(getApplication(), data.getData()), options);
                    new UploadPhotoAsyncTask(DropBoxClient.getClient(token), file, this).execute();
                }
            }
        }
    }

    /* Check if the user filled in a name in the inputfield upon clicking add */
    public boolean formValidation(String name) {
        // show error message id user fills in empty string
        if (name.length() == 0) {
            errorMes.setText(R.string.fill_in_all_fields);
            return false;
        }
        return true;
    }

    /* Calls galleryIntent() if the user clicks on the add button and the inputfield isn't empty. */
    public void addFeedbackClick (View v){

        if (!formValidation(etFBName.getText().toString())) {
            return;
        }

        FBName = etFBName.getText().toString();

        //if (checkPerm) {
            galleryIntent();
        //}
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    galleryIntent();
//                } else {
//                    Toast.makeText(this ,"Without permission no feedback can be uploaded", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    /** Gets called to start your gallery app on your phone with. */
    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_GALLERY);
    }

    /* saves dropbox path to database and calls currentSubjectIntent() */
    public void savePathToDB(String path) {
        helper.createPhoto(FBName, path, subject, UserId.getInstance().getUserId());
        currentSubjectIntent();
    }

    /** Is being called from the Asynctask to go back to this activity. */
    public void currentSubjectIntent() {
        Intent currentSubjectIntent = new Intent(this, CurrentSubjectActivity.class);
        currentSubjectIntent.putExtra("subjectName", subject);
        this.startActivity(currentSubjectIntent);
        finish();
    }
}
